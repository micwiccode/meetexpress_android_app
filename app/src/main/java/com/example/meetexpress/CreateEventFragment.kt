package com.example.meetexpress


import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_create_event.*
import kotlinx.android.synthetic.main.fragment_create_event.view.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateEventFragment : Fragment() {

    private val RESULT_LOAD_IMG = 1
    private val db = FirebaseFirestore.getInstance()
    private var year = 2019
    private var month = 1
    private var day = 1
    private var hour = 0
    private var minute = 0
    private lateinit var auth: FirebaseAuth
    private var storageRef = FirebaseStorage.getInstance().reference
    private var photoUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)
        auth = FirebaseAuth.getInstance()

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener {
            (activity as MenuActivity).openDrawer()
        }

        val spinner = layout.findViewById<Spinner>(R.id.categories_spinner)
        spinner.adapter = ArrayAdapter.createFromResource(
            activity!!.applicationContext,
            R.array.categories,
            android.R.layout.simple_spinner_dropdown_item
        )

        layout.btn_create.setOnClickListener {
            addEvent()
        }

        layout.btn_pick_photo.setOnClickListener {
            pickPhoto()
        }
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        setDate(year, month, day)
        setTime(hour, minute)

        date_box.setOnClickListener {
            showDatePicker()
        }

        time_box.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val tpd =
            TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, mHour, mMinute ->
                setTime(mHour, mMinute)
            }, hour, minute, true)
        tpd.show()
    }

    private fun showDatePicker() {
        val dpd = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                setDate(mYear, mMonth, mDay)
            },
            year, month, day
        )
        dpd.show()
    }

    private fun setTime(hour: Int, minute: Int) {
        time_text_view?.text =
            "" + String.format("%02d", hour) + ":" + String.format("%02d", minute)
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        date_text_view?.text = "" + String.format("%02d", day) + "-" + String.format(
            "%02d",
            month + 1
        ) + "-" + String.format("%02d", year)
    }

    private fun addEvent() {

        val name = input_text_name.text.toString()
        val maxPeople = input_text_members.text.toString()
        val place = input_text_place.text.toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(date_text_view.text.toString()).time
        val timeFormat = SimpleDateFormat("hh:mm")
        val time = timeFormat.parse(time_text_view.text.toString()).time
        val category = categories_spinner.selectedItem.toString()

        if (validate(name, maxPeople, place)) {

            val event = Event(
                name,
                date,
                time,
                1,
                maxPeople.toInt(),
                place,
                category,
                auth.currentUser!!.uid
            )

            val eventsCollection = db.collection("events")
            eventsCollection
                .add(event)
                .addOnSuccessListener { documentReference ->
                    Log.d("Dodawanie", "Event added with ID: ${documentReference.id}")
                    val photoStorageRef1 =
                        storageRef.child("events/" + documentReference.id + "/" + documentReference.id + "_1.jpg")
                    if (photoUri != null) {
                        Picasso.get()
                            .load(photoUri!!)
                            .into(object : Target {
                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                                }

                                override fun onBitmapFailed(
                                    e: Exception?,
                                    errorDrawable: Drawable?
                                ) {
                                }

                                override fun onBitmapLoaded(
                                    bitmap: Bitmap?,
                                    from: Picasso.LoadedFrom?
                                ) {


//                                    bitmap!!.height = bitmap.height/2
//                                    bitmap.width = bitmap.width/2
                                    val baos = ByteArrayOutputStream()
                                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 1, baos)
                                    val data = baos.toByteArray()
                                    photoStorageRef1.putBytes(data)
                                        .addOnSuccessListener {
                                            Log.d("XD", "Pierwsza git")
                                            val photoStorageRef2 =
                                                storageRef.child("events/" + documentReference.id + "/" + documentReference.id + "_2.jpg")
                                            val baos2 = ByteArrayOutputStream()
                                            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, baos2)
                                            val data2 = baos2.toByteArray()
                                            photoStorageRef2.putBytes(data2)
                                                .addOnSuccessListener {
                                                    Log.d("XD", "Druga git")
                                                    val photoStorageRef3 =
                                                        storageRef.child("events/" + documentReference.id + "/" + documentReference.id + "_3.jpg")
                                                    val baos3 = ByteArrayOutputStream()
                                                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos3)
                                                    val data3 = baos3.toByteArray()
                                                    photoStorageRef3.putBytes(data3)
                                                        .addOnSuccessListener {
                                                            Log.d("XD", "Trzecia git")
                                                        }
                                                        .addOnFailureListener {
                                                            Log.d("XD", it.message)
                                                        }
                                                }
                                                .addOnFailureListener {
                                                    Log.d("XD", it.message)
                                                }
                                        }
                                        .addOnFailureListener {
                                            Log.d("XD", it.message)
                                        }


                                }

                            })

                        Log.d("XD", photoUri!!.path)
//                        photoStorageRef.putFile(photoUri!!)
//                            .addOnSuccessListener {
//                                Log.d("XD", "Jest git")
//                            }
//                            .addOnFailureListener{
//                                Log.d("XD", it.message)
//                            }
                    }
                    eventsCollection
                        .document(documentReference.id)
                        .update("creatorId", auth.currentUser!!.uid)

                    db.collection("profiles")
                        .document(auth.currentUser!!.uid)
                        .update("hostedEvents", FieldValue.arrayUnion(documentReference.id))


                }
                .addOnFailureListener { e ->
                    Log.w("Dodawanie", "Error adding document", e)
                }


        }
    }

    private fun validate(name: String, maxPeople: String, place: String): Boolean {

        var isValid = true

        if (name.isEmpty()) {
            input_layout_name.error = "Name is required"
            isValid = false
        }
        if (maxPeople.isEmpty()) {
            input_layout_members.error = "Number of people is required"
            isValid = false
        }
        if (place.isEmpty()) {
            input_layout_place.error = "Place is required"
            isValid = false
        }
        return isValid
    }

    private fun pickPhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            try {
                val imageUri = data?.data!!
                photoUri = imageUri
                val imageStream = activity?.contentResolver?.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                setPickerBackground(selectedImage)
                Toast.makeText(activity, "Image selected", Toast.LENGTH_SHORT)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
        }
    }

    private fun setPickerBackground(image: Bitmap) {
        btn_pick_photo.setBackgroundResource(0)
        val scale = resources.displayMetrics.density.toInt()
        btn_pick_photo.layoutParams.height = 90 * scale
        btn_pick_photo.layoutParams.width = 90 * scale
        btn_pick_photo.requestLayout()
        btn_pick_photo.setImageBitmap(image)
    }
}



