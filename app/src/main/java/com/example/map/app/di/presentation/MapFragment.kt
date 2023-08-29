package com.example.map.app.di.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.map.R
import com.example.map.databinding.FragmentMapBinding
import com.example.map.utils.Constants
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import java.net.Socket

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private val startLocation = Point(59.9402, 30.315)
    private val marker = R.drawable.ic_pin_black_png

    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(activity)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.lastPosition = getCenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        navController = NavHostFragment.findNavController(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPosition()
        initMapObjects()
    }

    private fun initMapObjects() {
        val placeMarkCollection = binding.mapView.map.mapObjects.addCollection()
        viewModel.placeMarks.observe(viewLifecycleOwner, Observer { points ->
            points.forEach{ pointModel ->
                placeMarkCollection.addPlacemark(
                    Point(pointModel.latitude, pointModel.longitude),
                    ImageProvider.fromResource(context, marker))
            }
        })
    }

    private fun initPosition() {
        val lastPosition = viewModel.lastPosition
        if (lastPosition == null) {
            moveToLocation(startLocation, 3f)
        } else {
            moveToLocation(lastPosition, 0f)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popup_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list -> navController.navigate(R.id.pointsListFragment)
            R.id.save -> showDialog()
        }
        val socket = Socket("192.168.0.1", 8080)
        socket.getInputStream().read()
        return super.onOptionsItemSelected(item)
    }


    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        val saveButton = dialog.findViewById(R.id.save_button) as Button
        val longitudeTv = dialog.findViewById(R.id.longitude) as TextView
        val latitudeTv = dialog.findViewById(R.id.latitude) as TextView

        val center = getCenter()
        longitudeTv.text = formatValue(center.longitude)
        latitudeTv.text = formatValue(center.latitude)

        saveButton.setOnClickListener {
            viewModel.addPoint(Point(center.latitude, center.longitude))
            setMarker(Point(center.latitude, center.longitude))
            dialog.hide()
            Toast.makeText(activity, "Добавлено", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    private fun formatValue(value: Double) = String.format("%.4f", value) + "°"

    private fun getCenter() : Point {
        val width = binding.mapView.width() / 2f
        val height = binding.mapView.height() / 2f

        return binding.mapView.screenToWorld(ScreenPoint(width, height))
    }

    private fun moveToLocation(location: Point, animation: Float) {
        binding.mapView.map.move(
            CameraPosition(location, Constants.ZOOM_VALUE, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, animation),
            null
        )
    }

    private fun setMarker(point: Point) {
        mapObjectCollection = binding.mapView.map.mapObjects
        placemarkMapObject = mapObjectCollection
            .addPlacemark(point, ImageProvider.fromResource(context, marker))
        placemarkMapObject.opacity = 0.7f
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}