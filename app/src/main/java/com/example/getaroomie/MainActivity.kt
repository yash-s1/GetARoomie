package com.example.getaroomie


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        //path of database
        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            //snapshot to get the data
                //for loop for checking all users 1 by 1
                userList.clear()


                //test
                lateinit var loggedinuser:User
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(mAuth.currentUser?.uid == currentUser?.uid){
                        loggedinuser = currentUser!!
                    }

                }

                for(postSnapshot in snapshot.children){

                    val currentUser = postSnapshot.getValue(User::class.java)

                    var count:Int = 0

                    if (currentUser?.movies == true && loggedinuser.movies) count++
                    if (currentUser?.music == true && loggedinuser.music) count++
                    if (currentUser?.cycling == true && loggedinuser.cycling) count++
                    if (currentUser?.swimming == true && loggedinuser.swimming) count++
                    if (currentUser?.dancing == true && loggedinuser.dancing) count++



                    //adding to userList only id uid of current user is not same as the uid of list user
                    if(mAuth.currentUser?.uid != currentUser?.uid && count >= 3){
                        userList.add(currentUser!!)
                    }



                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            //logic for logout
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LogIn::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}