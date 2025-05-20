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
import com.uax.extretur.adapters.AdaptadorMonumentos
import com.uax.extretur.databinding.ActivityMonumentsBinding
import com.uax.extretur.model.Monumento

class MonumentsActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var binding: ActivityMonumentsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaMonumentos: ArrayList<Monumento>
    private lateinit var adaptadorMonumentos: AdaptadorMonumentos

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMonumentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navMonuments.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navMonuments.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    binding.navMonuments.navLayout.menu.findItem(R.id.foro).itemId -> {
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
                    binding.navMonuments.navLayout.menu.findItem(R.id.perfil).itemId -> {
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
        listaMonumentos = arrayListOf(
            Monumento(
                "Acueducto Los Milagros",
                "Acueducto romano en Mérida",
                "Mérida",
                "Badajoz",
                "https://turismomerida.org/assets/uploads/2018/01/acueducto-milagros02.jpg", 0.0, 0.0
            ),
            Monumento(
                "Monasterio de Tentudía",
                "En un cerro de la Sierra de Tentudía, a 9km de Calera de León al sur de Badajoz, se encuentra el Monasterio de Tentudía, una excelente visita para aquellos que quieran disfrutar de uno de los mejores exponentes del arte mudéjar español.\n" +
                        "\n" +
                        "Además, este templo dedicado a Santa María de Tentudía está rodeado de hermosos parajes naturales que quitarán el aliento a cualquiera.El origen de este monasterio del siglo XIII está relacionado con una curiosa leyenda. Durante una batalla contra los árabes, el Capitán Pelay Pérez Correa, maestre de la Orden de Santiago, al ver que caía la noche y la victoria no llegaba, pidió a la Virgen: “Santa María, detén tu día”.Cuenta la leyenda que, ante la petición del capitán, el sol se detuvo en el horizonte, alargando el día hasta que los cristianos obtuvieron la victoria. El maestre Pérez Correa, en agradecimiento a la Virgen, mandó a construir una ermita dedicada a Santa María de Tudía o Tentudía.\n" +
                        "\n" +
                        "Esta ermita hecha de piedra y mampuesto, fue ampliada desde entonces en varias ocasiones por los miembros de la Orden de Santiago. Posteriormente, con la construcción del Conventual en Calera de León, la zona adquirió mayor relevancia, convirtiéndose en uno de los principales centros de los santiaguistas.",
                "Calera de León","Badajoz",
                "https://i.postimg.cc/D8fG8XcD/tentudia.jpg" , 38.05438116195432, -6.338450641858771
            ),
            Monumento(
                "Capilla sixtina extremeña",
                "La ermita de Nuestra Señora del Ara se encuentra a unos siete kilómetros de la localidad pacense de Fuente del Arco y a pocos kilómetros de la misma frontera con Andalucía. Está situada en plena Sierra Morena, en un paraje rodeado de olivos. Algunos estudios aseguran que es la única ermita de España cuyo interior está totalmente decorado con pinturas en la totalidad de sus muros y techos ",
                "Fuente del Arco","Badajoz",
                "https://www.canalextremadura.es/sites/default/files/styles/full_content_sidebar_large/public/Media/Images/2021-02/A_ara_9_0.jpg?itok=97fU20MS",
                38.131714, -5.940163
            ),
            Monumento(
                "Granadilla",
                "Granadilla, antigua villa amurallada de origen feudal en el norte de la provincia de Cáceres, pertenece al municipio de Zarza de Granadilla. Fue un importante pueblo, capital de la comarca y cabeza de partido judicial. Desalojada a mediados del S.XX, al declararse zona inundable, en la actualidad es Conjunto histórico-artístico y está incluido en el Programa Interministerial de Recuperación y Utilización Educativa de Pueblos Abandonados\n" +
                        "\n" +
                        "¿Por qué se abandonó Granadilla?\n" +
                        "Granadilla no se abandonó, sería más correcto decir que se desalojó. El 24 de junio de 1955, el gobierno franquista acordó la expropiación de Granadilla y gran parte de su término municipal para la construcción del embalse de Gabriel y Galán.",
                "Granadilla","Cáceres",
                "https://visitargranadilla.com/wp-content/uploads/2021/11/castillo-granadilla-entrada.jpg" , 40.26822329319545, -6.106470000000001
            ),
            Monumento(
                "Ciudad Romana de Cáparra",
                "Enclavada en el norte de Cáceres, cerca de Plasencia, Cáparra emerge como un testimonio tangible del esplendor del pasado romano. Las ruinas revelan la grandeza arquitectónica y cultural de una ciudad estratégicamente situada en el eje norte-sur de la Vía de la Plata, conectando Emérita Augusta (Mérida) y Artúrica Augusta (Astorga)."+"\n"+
                        "Hoy en día, los visitantes pueden maravillarse con las ruinas bien conservadas de Cáparra. Recorrer sus calles empedradas y contemplar sus monumentos es como dar un paso atrás en el tiempo hacia la grandeza de la antigua Roma. Además, la belleza natural que rodea a la ciudad, con sus paisajes de montaña añade un encanto único a la experiencia.",
                "Guijo de Granadilla","Cáceres",
                "https://visitargranadilla.com/wp-content/uploads/2022/11/ciudad-caparra-1.webp" , 40.16658333, -6.10097222
            ),
            Monumento(
                "Monumento Natural Los Barruecos",
                "Ubicado en Malpartida de Cáceres, Los Barruecos presenta un paisaje espectacular dominado por grandes bolos graníticos, esculpidos por los agentes erosivos durante miles de años. Las caprichosas formas resultantes presentan el más extenso catálogo de modelado sobre granito, lo cual ha atraído la atención de geólogos y geomorfólogos de todo el mundo. El paisaje se completa con la presencia de cuatro embalses históricos, en cuyas aguas se reflejan las formas graníticas creando la sensación de un paisaje ilusorio."+"\n"+"En 1976 el artista hispano alemán Wolf Vostell crea en Malpartida de Cáceres un museo de referencia de las vanguardias europeas. El Museo se instala en el antiguo edificio del Lavadero de lanas, de finales del S. XVIII, declarado Bien de Interés Cultural-Sitio histórico. "+ "\n"+ "Como curiosidad, en el año 2016 el paraje del Barrueco de Arriba fue elegido por la productora HBO para el rodaje de la Serie Juego de Tronos: una gran batalla de 24’ de duración en la que se enfrentaban los ejércitos Lannister contra las hordas Dothrakis, con la intervención decisiva de Drogon dirigido por Khaalesi. Una batalla de fuego que fue emitida entre los capítulos 4 y 5 de la séptima temporada.",
                "Malpartida de Cáceres","Cáceres",
                "https://www.malpartidadecaceres.es/images/Barruecos5.jpg", 0.0, 0.0
            ),
            Monumento(
                "Jerez de los Caballeros",
                "Casi de frontera entre España y Portugal, Jerez de los Caballeros es un pueblo de 10.000 habitantes de Badajoz, en la que se puede observar la profunda huella dejada por los Caballeros Templarios. En el siglo XIII, bajo la orden del rey Alfonso IX, los templarios tomaron este pueblo e hicieron a Jerez de los Caballeros capital del Bayliato de Xerez, compuesta por una zona de la que formaban parte los pueblos de alrededor.",
                "Jerez de los Caballeros","Badajoz",
                "https://www.turismobadajoz.es/wp-content/uploads/2020/08/muralla-jerez-de-los-caballeros.jpg", 38.32134024596816, -6.7754435127700905
            ),
            Monumento(
                "Teatro Romano de Mérida",
                "Es la joya de todo el legado romano que nos ha llegado hasta nuestros días. Con más de 2.000 años de historia, el Teatro Romano continúa con la misma función con la que se construyó en el año 16 a. C., es decir, servir como escenario para representaciones culturales. Aquí se siguen celebrando todos los veranos el famoso Festival de Teatro Clásico de Mérida. Las gradas tienen tres alturas donde se situaban las personas, según status social de la época. Con una capacidad para 5.800 espectadores. Otras partes importantes son la orchestra, las gradas reservadas para la alta sociedad, el frente de escena con las puertas de acceso, el conjunto de columnas y las esculturas que representaban a personajes mitológicos y a dioses. El Teatro se completaba con un jardín en la parte posterior, conocido como Peristilo del que aún se conservan los canalillos para el riego.",
                "Mérida","Badajoz",
                "https://www.turismobadajoz.es/wp-content/uploads/2019/07/teatro-romano-de-merida.jpg", 38.91653594429036, -6.339363874029803
            ),
            Monumento (
                "Anfiteatro de Mérida",
                "El Anfiteatro de Mérida se construyó en el año 8 a.C. y tenía una capacidad para unos 15.000 espectadores. Considerado por los romanos el recinto de ocio por excelencia. Aquí se celebraban las luchas entre gladiadores y fieras, la representación de batallas históricas o los combates entre los gladiadores.",
                "Mérida", "Badajoz",
                "https://www.turismobadajoz.es/wp-content/uploads/2019/07/entrada-anfiteatro-romano-merida.jpg", 38.91613455860237, -6.337960384029423
            ),
            Monumento (
                "Circo Romano",
                "Está situado a las afueras de la ciudad. Se construyó a principios del siglo I d.C. y constituía otro de los edificios dedicados al ocio de los romanos. Aquí tenían lugar las famosas carreras de bigas, que eran carros tirados por dos caballos y las cuadrigas que era lo mismo, pero con cuatro caballos.\n" +
                        "\n" +
                        "El Circo tiene 400 metros de longitud y unos 115 de ancho. Tenía una capacidad para más de 30.000 personas, uno de los más grandes de todo el Imperio Romano. ",
                "Mérida","Badajoz", "https://www.turismobadajoz.es/wp-content/uploads/2019/07/Depositphotos_272230120_s-2019.jpg", 38.920415664366054, -6.333185186378999
            ),
            Monumento (
                "Templo de Diana",
                "Estaba situado dentro del Foro Municipal de Emerita Augusta, que era el centro de la vida pública. Punto de encuentro de los ciudadanos, donde transcurría la vida política, jurídica, donde se realizaban las transacciones económicas y mercantiles, así como lugar de culto a los dioses. Uno de los templos principales fue el Templo de Diana, situado en el centro de la plaza porticada, donde se celebraban actos religiosos al aire libre. El templo estaba rodeado por dos estanques. Su construcción data del siglo 25 a. C. ",
                "Mérida","Badajoz", "https://www.turismobadajoz.es/wp-content/uploads/2019/07/Templo-diana-merida.jpg", 38.91643832414665, -6.344204268575879
            ),
            Monumento (
                "Puente Romano de Mérida",
                "Estamos frente a uno de los puentes romanos más largos impresionantes de todo el Imperio Romano, con una longitud de casi 800 metros.\n" +
                        "\n" +
                        "Este puente se utilizó como paso de vehículos hasta finales del siglo XX. Pero esta joya de la ingeniería romana estaba sufriendo un deterioro importante, entre otros motivos, por las crecidas del río Guadiana. Por ello, se realizaron bastantes reformas a lo largo de los siglos. Originalmente construido en hormigón forrado de sillares de granito, hoy tiene 60 arcos de medio punto y doce metros de alto, en las zonas más elevadas.\n" +
                        "\n" +
                        "Actualmente el puente es peatonal. De hecho, es un lugar muy transitado por los emeritenses y turistas que llegan a la ciudad. Conecta el parque de la Isla con el de las Siete Sillas. ",
                "Mérida","Badajoz", "https://www.turismobadajoz.es/wp-content/uploads/2019/07/Puente-Romano-Merida.jpg", 38.91374610421187, -6.350078219969008
            ),
            Monumento (
                "Guadalupe",
                "Guadalupe no deja indiferente a nadie y suele cautivar y fidelizar a quienes lo visitan. \n La historia de Guadalupe está ligada con la del Real Monasterio de Santa María de Guadalupe, monumento e icono turístico por excelencia de este enclave, reconocido por la U.N.E.S.C.O. como Patrimonio de la Humanidad desde el año 1993. Guadalupe es uno de los Pueblos Más Bonitos de España desde el año 2017, fue delarado Primera Maravilla Rural de España en ese mismo año y finalista a la Capital rural de España en 2020. En el 2022 ha sido reconocido como Pueblo más Turistico del Mundo (Best Tourism Villages) por la Organización Mundial del Turismo. ",
                "Guadalupe","Cáceres", "https://www.guadalupeturismo.com/wp-content/uploads/2022/12/guadalupe-interior.jpg", 39.452022689463746, -5.3272371258805515
            ),
            Monumento (
                "Ciudad Monumental de Cáceres",
                " La Ciudad Monumental de Cáceres conserva un conjunto histórico-artístico único que, en 1986,  fue reconocida por la UNESCO como Ciudad Patrimonio de la Humanidad.\n" +
                        "Calles, plazas, palacios, iglesias y murallas, en un magnífico estado de conservación, serán capaces de trasladarte a un glorioso pasado, en el que saborearás las maravillosas manifestaciones artísticas que las tres culturas: cristiana, musulmana y judía, dejaron en nuestra ciudad. Esta diversidad te permitirá adentrarte en la Ciudad Monumental atravesando una puerta romana, encontrarte con la preciosa Judería Vieja a la salida de una torre almohade o contemplar un palacio renacentista junto a una fachada mudéjar. La mezcla cultural nos ha legado un excelente Patrimonio Monumental a la vez que ha forjado el carácter hospitalario de los cacereños.",
                "Cáceres","Cáceres",
                "https://turismo.caceres.es/sites/default/files/styles/simplecrop__16_9_/public/yincana/Arco-estrella-interior%5B1%5D.jpg?itok=1DaDIir0&sc=e8d1fa716530ff98c012cbc8d9b63d8d", 39.47512144834059, -6.371458402397456
            ),
            Monumento (
                "Real Monasterio de Yuste",
                "El Monasterio de Yuste es conocido mundialmente por ser la última morada del Emperador Carlos V, y en la actualidad es la sede de la ceremonia de entrega del Premio Europeo Carlos V en un acto que preside S.M. el Rey. En un entorno privilegiado que incluye itinerarios naturales, la visita al Monasterio permite descubrir la Casa-Palacio, la iglesia y los Claustros Gótico y Renacentista.",
                "Cuacos de Yuste","Cáceres",
                "https://www.patrimonionacional.es/sites/default/files/styles/full/public/2020-05/03-img_1940.jpg?itok=pWSH-4Rg", 40.113616883076396, -5.738496904953738
            ),
            Monumento (
                "Trujillo",
                "Esta localidad cacereña puede presumir de un patrimonio impresionante aunque es mucho más que un señorío feudal y sus encantos van más allá de las almenas. Entre su vieja alcazaba y su monumental Plaza Mayor se teje una red de callejuelas donde, sin apenas intuirlo, aparece un palacio renacentista o una torre lombarda. Y es que Trujillo es, precisamente, un destino cultural insospechado disfrazado de pueblo bonito. Pero aquí nada es lo que parece. ",
                "Trujillo","Cáceres",
                "https://trujilloteespera.com/wp-content/uploads/2021/09/estatua-ecuestre-fracisco-pizarro.jpg", 39.460529761420865, -5.881398281163209
            ),
            Monumento (
                "Meandro del Melero",
                "Si hay una imagen que identifica y representa el norte de la provincia de Cáceres esa es la del meandro El Melero. En el límite oriental de la sierra de Gata, dentro de la comarca de las Hurdes y dibujando la frontera entre Cáceres y Salamanca, podemos observar este espectacular meandro que forma el río Alagón.\n" +
                        "\n" +
                        "La localidad más cercana es Riomalo de Abajo, dependiente de Caminomorisco y que constituye un excelente punto de partida para la visita de este capricho del río. En esta alquería podemos contemplar ejemplos de la arquitectura hurdana y aprovechar para reponer fuerzas antes de comenzar la senda, aunque no es especialmente larga ni exigente (no llega a 3 kilómetros)\n" +
                        "\n" +
                        "Saliendo de Riomalo de Abajo nos encontramos rápidamente con una piscina natural que se forma sobre el río Ladrillar. Un camino nos conduce hasta las inmediaciones del meandro. Podemos recorrerlo a pie o en coche, pero andarlo nos dejará mejores recuerdos de los característicos paisajes de Las Hurdes. \n Es un lugar que no solo invita a la contemplación. También podemos participar de descensos en canoa o catamarán, de la pesca o del avistamiento de aves. El mirador nos ofrece una amplia panorámica sobre el río y las sierras que lo rodean, siendo un lugar perfecto para observar aves como la cigüeña negra, el buitre negro y leonado, el águila calzada o el águila culebrera.",
                "Riomalo de Abajo","Cáceres",
                "https://www.turismocaceres.org/sites/default/files/imagenes/naturaleza/melero2.jpg", 40.390072854956465, -6.08177963635864
            ),
            Monumento (
                "Puente Romano de Alcántara",
                "El puente de Alcántara, de origen romano, fue construido entre los años 104 y 106 y declarado, en 1924, Bien de Interés Cultural con categoría de Monumento.\n" +
                        "\n" +
                        "Destaca, ante todo, por su grandeza y por la firmeza que hoy en día conserva, casi intacta a pesar de tener casi dos mil años de antigüedad. Sus 58,20 metros de altura y 194 metros de longitud lo convierten en una bella obra, a caballo entre la ingeniería y el arte.\n" +
                        "\n" +
                        "La ubicación estratégica del monumento lo ha convertido en un enclave estratégico con el paso de los siglos, salvando el cauce del río Tajo y formando parte de una de las calzadas que comunica España con Portugal.\n" +
                        "\n" +
                        "Este descomunal monumento ocupa un lugar destacado entre todas las obras de ingeniería realizadas por el Imperio Romano. Se levantó con el objetivo de facilitar la comunicación entre Norba (la actual Cáceres) y Conimbriga (la localidad portuguesa de Condeixa-a-Velha) y es considerado una obra maestra donde se aplicaron técnicas de ingeniería avanzadas para erigir seis arcos de medio punto, sostenidos por cinco gruesos pilares. El puente fue construido con sillares rectangulares unidos entre sí a soga y tizón. La Villa de Alcántara nació años después. Como también posteriormente el Puente fue clave para la orden militar de Alcántara, en la defensa de la Villa, fronteriza con Portugal.",
                "Alcántara","Cáceres",
                "https://www.turismocaceres.org/sites/default/files/imagenes/cultural/panorama_alcantara_2.jpg", 39.72237962365203, -6.8923547761558455
            ),
            Monumento (
                "Templo de los mármoles",
                "En Bohonal de Ibor, al noreste de la provincia de Cáceres, se encuentran los restos de la antigua ciudad romana de Augustóbriga, enclavada bajo la localidad de Talavera la Vieja. \n" +
                        "\n" +
                        "Al construirse el pantano de Valdecañas, en 1960, el lugar quedó inundado por las aguas del embalse. \n" +
                        "\n" +
                        "La ciudad de Augustóbriga se asentaba en la hondonada que hoy cubre dicho pantano y se correspondía con la población extremeña de Talavera la Vieja (conocida como Talaverilla por sus propios paisanos) que llegó a tener 2.000 habitantes antes de su desaparición. \n" +
                        "\n" +
                        "De Augustóbriga quedan el templo y otras referencias históricas de cómo fue la ciudad gracias a documentos de Cornide y Hermosilla, en el siglo XVIII y, posteriormente, a Mélida.\n" +
                        "\n" +
                        "De época romana sabemos que las murallas protegieron y rodearon la ciudad. En el centro se dispuso el foro, rodeado de edificios administrativos y religiosos. \n" +
                        "\n" +
                        "Destacan sobre el resto de vestigios, los restos del templo conocido como \"Los Mármoles\", del s. II, que se desmontaron piedra a piedra para reconstruirlo en un entrante de tierra por encima del nivel máximo de ocupación de las aguas del pantano, a 6,5 kilómetros de distancia de su establecimiento primitivo. Forman su pórtico o fachada principal cuatro columnas delanteras y dos laterales sobre las que descansa el arquitrabe y, sobre éste, un arquito de medio punto. El edificio está construido en granito.",
                "Bohonal de Ibor","Cáceres",
                "https://www.turismocaceres.org/sites/default/files/imagenes/cultural/augustobriga_9913.jpg", 39.80414411861822, -5.404994185828184
            ),
            Monumento(
                "Ciudad de Badajoz",
                "El carácter fronterizo de la ciudad de Badajoz desde su fundación en 875 le ha conferido un marcado carácter militar que, a pesar de haber sido muy castigada por numerosas guerras, conserva multitud de edificios declarados Bien de Interés Cultural, con un importante complejo histórico y arqueológico en su parte monumental.\n Destacan su Alcazaba (la mayor de Europa), la plaza Alta, la Puerta de Palmas y el puente de Palmas, la catedral de San Juan Bautista y la torre de Espantaperros.\n  En la actualidad, sus buenas relaciones transfronterizas con la portuguesa y vecina Elvas han dado lugar a un acuerdo, desde 2013, como eurociudad, para impulsar un crecimiento conjunto.",
                "Badajoz","Badajoz",
                "https://www.turismobadajoz.es/wp-content/uploads/2020/09/PLAZA-ALTA-BADAJOZ-1024x684.jpg", 38.878820284926014, -6.969703360032985
            )
        )

        adaptadorMonumentos = AdaptadorMonumentos(listaMonumentos, this)

        if (resources.configuration.orientation == 1) {
            binding.monumentCards.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.monumentCards.layoutManager = GridLayoutManager(this, 2)
        }
        binding.monumentCards.adapter = adaptadorMonumentos
    }

    private fun acciones() {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val listaFiltrada: ArrayList<Monumento> = listaMonumentos.filter {
            it.provincia.equals("Badajoz")
        } as ArrayList<Monumento>
        adaptadorMonumentos.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}