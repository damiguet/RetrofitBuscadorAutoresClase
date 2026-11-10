package com.dam.retrofitbuscadorautores

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView=findViewById(R.id.rvFavoritos)
        recyclerView.layoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val adaptador= AdaptadorMisFavs()
        recyclerView.adapter=adaptador

        lifecycleScope.launch(Dispatchers.IO) {
            val database= AutoresBBDD.getInstance(applicationContext)
            val datos=database.autoresDAO().selectAll() as MutableList<Autores>
            withContext(Dispatchers.Main){
                adaptador.changelist(datos)
                adaptador.notifyDataSetChanged()
            }
        }
    }
}