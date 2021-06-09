package com.example.allmen.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.allmen.R
import com.example.allmen.databinding.FragmentHomeBinding
import com.example.allmen.ml.ModelMLofFruit
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val fileName = "labels.txt"
        val inputString =
            activity?.application?.assets?.open(fileName)?.bufferedReader().use { it?.readText() }
        val townList = inputString?.split("\n")

        binding.button.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    activity, "Sorry for the error hehe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.button2.setOnClickListener {
            binding.why.visibility = View.VISIBLE

            val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
            val model = activity?.let { it1 -> ModelMLofFruit.newInstance(it1) }

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)

            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(resized)
            val byteBuffer: ByteBuffer = tensorImage.buffer

            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model?.process(inputFeature0)
            val outputFeature0 = outputs?.outputFeature0AsTensorBuffer

            val max = outputFeature0?.floatArray?.let { it1 -> getMax(it1) }

            val resultname = townList?.get(max!!).toString()
            binding.textView.text = resultname

            configFruitsProperties(resultname)

            model?.close()
        }

        return view

    }

    private fun configFruitsProperties(resultname: String) {
        when (resultname) {
            "Apple" -> {
                binding.textView2.setText(R.string.safetoeat)
                binding.textView3.setText(R.string.apple)
            }
            "Banana" -> {
                binding.textView2.setText(R.string.notsafetoeat)
                binding.textView3.setText(R.string.banana)
            }
            "Corn" -> {
                binding.textView2.setText(R.string.safetoeat)
                binding.textView3.setText(R.string.corn)
            }
            "Orange" -> {
                binding.textView2.setText(R.string.safetoeat)
                binding.textView3.setText(R.string.orange)
            }
            "Peach" -> {
                binding.textView2.setText(R.string.safetoeat)
                binding.textView3.setText(R.string.peach)
            }
            "Strawberry" -> {
                binding.textView2.setText(R.string.safetoeat)
                binding.textView3.setText(R.string.strawberry)
            }
            "Watermelon" -> {
                binding.textView2.setText(R.string.notsafetoeat)
                binding.textView3.setText(R.string.watermelon)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView2.setImageBitmap(bitmap)
        }
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..6) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i
            }
        }
        return ind
    }
}