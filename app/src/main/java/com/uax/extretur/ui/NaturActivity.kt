package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorNatur
import com.uax.extretur.databinding.ActivityNaturBinding
import com.uax.extretur.model.Natur

class NaturActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityNaturBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaNatur: ArrayList<Natur>
    private lateinit var adaptadorNatur: AdaptadorNatur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNaturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navNatur.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navNatur.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    binding.navNatur.navLayout.menu.findItem(R.id.foro).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ForumActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }

                    binding.navNatur.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }

                    else -> false
                }
            }
        })

        instancias()
        acciones()
    }


    private fun instancias() {
        listaNatur = arrayListOf(
            Natur(
                "Crucero por el Tajo",
                "Desde su nacimiento, Barco del Tajo tiene como objetivo dar a conocer de una manera especial y diferente rincones naturales existentes en Extremadura y que hasta ahora se habían mostrado desde otros puntos de vista. Se trata de cruceros fluviales por el Tajo en los rincones más puros de Extremadura dónde se realza la naturaleza y brilla el sol sobre las aguas que recorren esta tierra, navegan nuestras DOS embarcaciones surcando el cauce del río en DOS zonas restringidas, admiradas por su gran belleza: \n - Barco de Ceclavín - Canchos de Ramiro: es una embarcación en la que podrás disfrutar de distintas rutas por la zona ZEPA Alcántara - Canchos de Ramiro, disfrutando de parajes espectaculaes. \n - Barco de Monfragüe: esta embarcación realiza rutas en la zona de la Reserva de la Biosfera de Monfragüe, un maravilloso paraje con una gran riqueza natural. \n Puedes consultar tarifas y realizar la reserva en: https://barcodeltajo.com/ ",
                "Plasencia/Monfragüe", "Crucero", "Baja",
                "https://barcodeltajo.com/wp-content/uploads/2025/02/vista_dron_ceclavin-e1739982590836.jpg"
            ),
            Natur(
                "Rutas 4x4 en Villuercas",
                "Se trata de rutas en 4 x 4 por el Geoparque Villuercas Ibores Jara, ubicado a las inmediaciones de Guadalupe (Cáceres).\n Ofrecen cuatro rutas diferentes, pero la más demandada por quienes visitan esta ubicación por 1ª vez es 'El Geoparque a vista de pájaro'. En ella tendrás oportunidad de llevarte una idea general muy clara del Geoparque de las Villuercas, conocer sus fósiles, sus geositios más emblemáticos y sus espectaculares paisajes."
                        +"\n"+"Puedes realizar la reserva en: https://www.rutasgeoparquevilluercas.com/visitas-guiadas-en-4x4",
                "Guadalupe", "4x4", "Baja",
                "https://le-cdn.website-editor.net/s/f788d64a6da24e79bfc170440314be0d/dms3rep/multi/opt/PSX_20220219_203647-ca7cc4bc-21c1c224-1920w.jpg?Expires=1749745780&Signature=T65fIOx407HqnFzsUMnxlShlD4EKd3115ThVIwAhTMLNjLJbTyEyFo6pMFeF~qj1IEf6eAQKlqdIc-wWEn1tmtjjhwAnDREO5G7THHgBTGl9kY4ZZQ-2AjX40p-SkZtqW20GD5qPW3nO~VHK9LFVpXtkMB~9AKdZqiNWo8n42i4HqPIECSO982AYr3kQ7B5cq1NxiaiKy9L7fTVgt4oHhAyo8n6lzQBeN4agDGmuhs7gSmbQS89fXlv5IBaKS4midHmYDOUcKV-96Fpn6yMMMU31CAhA6QF-QmGv56AP~OrxLOv1w8iwadjkjK3yMTNjdMt13C3TdzJUVE1-kdUorQ__&Key-Pair-Id=K2NXBXLF010TJW"
            ),
            Natur(
                "Ruta 4x4 Cerezo en Flor",
                "Descubre los lugares desconocidos del Valle del Jerte, así como emplazamientos únicos con las mejores panorámicas del Valle para ver la floración de más de 1 millón de cerezos.\n" +
                        "\n" +
                        "Veremos Cascadas de agua cristalina, pueblos bien conservados y la Reserva Natural Garganta de los Infiernos."+
                        "\nPuede consultar tarifas, realizar la reserva o explorar más actividades en: https://www.monfraguenatural.com/ruta-4x4-cerezo-en-flor-valle-del-jerte-2/",
                "Valle del Jerte", "4x4", "Baja",
                "https://mrplan.io/experiencias/lib/imagen_exp.php?id_experiencia=13182&orden=01&ordenbd=1&ampl=1&ajustar_imagen=288_220"
            ),
            Natur(
                "Extremadura en Globo",
                "Volarás sobre las maravillosas dehesas de Extremadura, diferentes pueblos, sobre viñas, las ciudades patrimonio de la Humanidad como (Mérida y Cáceres) para que seas testigo del encanto paisajístico de Extremadura., "
                        +"\nPuedes obtener más información en: https://extremaduraenglobo.com/",
                "Mérida", "Globo", "Baja",
                "https://extremaduraenglobo.com/volarenglobo/wp-content/uploads/Caceres-en-globo-1-300x200.jpg"
            ),
            Natur(
                "Birding en Monfragüe",
                "Para los amantes de la ornitología hacer birding en el Parque Nacional Monfragüe es una increíble experiencia. Referente a nivel europeo, Monfragüe cuenta con gran variedad de ecosistemas, que aportan una diversidad de paisajes naturales únicos y con un estado de conservación elevado. Esto, unido al uso sostenible que este espacio natural ha tenido durante cientos de años hace que se convierta en un paraíso natural para las aves. \n El equipo de guías selecciona los mejores lugares de Monfragüe para observar aves, garantizando estar en el sitio adecuado en cada época del año.\n" +
                        "\n" +
                        "Las rutas incluyen roquedos dentro del Parque Nacional, zonas de monte y matorral mediterráneo, dehesas, arroyos y ríos, así como la zona de lagunas del Embalse de Arrocampo.   "
                        +"Puedes obtener más información así como realizar la reserva en: https://destinoactivo.com/tour/birding-parque-nacional-monfrague/",
                "Plasencia/Monfragüe", "Birding", "Baja",
                "https://destinoactivo.com/wp-content/uploads/2021/09/Buitre_leonado_Monfrague.webp"
            ),
            Natur(
                "Barranquismo (iniciación)",
                "Cerca del pueblo de Guijo de Santa Bárbara, cuna de Viriato, y también al lado del charco del Puente de la Máquina y de sendos chiringuitos. Este barranco hará las delicias de cualquier grupo: iniciación, familias, amigos. Haciendo la mitad, del puente para abajo, es el barranco ideal para una primera toma de contacto de la actividad y para familias con niños.\nObtén más información sobre esta y más actividades en la zona de La Vera en: https://www.actionvera.com/actividades/descenso-del-barranco-el-garganton",
                "Vera", "Barranquismo", "Media",
                "https://www.actionvera.com/wp-content/uploads/2024/02/El-Garganton-Action-Vera-1.jpg"
            ),
            Natur(
                "Isla del Zújar",
                "En el complejo Isla del Zújar disponen de una amplia gama de actividades de ocio y tiempo libre, todas ellas desarrolladas  en un entorno natural y adaptadas a cada perfil de los participantes.\nTienen 20 años de experiencia y es una de las empresas pioneras en el sector del turismo activo en Extremadura. El público puede disfrutar de la Isla del Zújar a partir de los 3 años, practicando, juegos, deportes, aventura, talleres, campamentos y mucho más. Cuentan con un amplio y cuidado programa orientado a colectivos (centros educativos, asociaciones, grupos y familias).\nPuedes obtener más información de todas las actividades que realizan en https://isladelzujar.com/actividades-en-la-isla-del-zujar/",
                "La Serena", "Varios", "Baja",
                "https://isladelzujar.com/wp-content/uploads/2016/10/isla-aventura6.jpg"
            ),
            Natur(
                "Astroturismo",
                "Realizan actividades como " +
                        "\n- Observaciones astronómicas: se trata de un tour guiado a simple vista por las estrellas y constelaciones más importantes, descubriendo el origen de sus nombres y su relación con la mitología griega. También podrás utilizar un telescopio profesional para disfrutas de una visión distinta de la bóveda celeste. " +
                        "\n- Observacions urbanas: en Extremadura todavía disfrutamos de  unos  cielos  urbanos que nos permiten contemplar algunas de las maravillas del universo, por lo que no tendrás que alejarte de la ciudad para admirar el espectáculo cuando el Sol se oculta. " +
                        "\n- Escapadas privadas: sesión astronómica en exclusiva para ti o en compañía de quien tú elijas." +
                        " Puedes obtener más información de los próximos eventos y realizar una reserva en: https://astroturismoporextremadura.es/",
                "Mérida", "Varios", "Baja",
                "https://astroturismoporextremadura.es/wp-content/uploads/2022/04/33-768x996.jpg"
            ),
            Natur(
                "Ruta a caballo",
                "La Dehesa Extremeña tiene una orografía suave con encinares y arroyos, por la que es una delicia pasear a caballo y deleitarse con el paisaje, la fauna salvaje y la ganadería en extensivo de la zona. \nEl centro ecuestre se encuentra próximo al monumento Natural de los Barruecos y a la sierra de San Pedro, provincia de Cáceres." +
                        "Se pueden realizar cualquier día de la semana reservando con antelación (para grupos de dos personas mínimo). Si no tienes experiencia, te imparten una clase de manejo en la pista antes de salir con caballos. " +
                        "Puedes consultar más información en https://www.romeral.es/rutas.html",
                "Cáceres", "Varios", "Media",
                "https://www.romeral.es/images/actividades/ruta3.JPG"
            )
        )


        adaptadorNatur = AdaptadorNatur(listaNatur, this)

        if (resources.configuration.orientation == 1) {
            binding.naturCards.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.naturCards.layoutManager = GridLayoutManager(this, 2)
        }

        binding.naturCards.adapter = adaptadorNatur

    }

    private fun acciones() {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val listaFiltrada: ArrayList<Natur> = listaNatur.filter {
            it.dificultad.equals("Baja")
        } as ArrayList<Natur>
        adaptadorNatur.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}