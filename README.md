# CinemaNice

 Este proyecto permite presentar los diferentes videos por tipo (Popular, TopRate y UpComing), permite ver el detalle de cada película seleccionada, funciona Online o bien OffLine. Tiene un buscador de películas Online y Offline.



# ¿En que consiste el principio de responsabilidad única y cual es su propósito?

El principio de responsabilidad única pertenece a los principios SOLID y establece que para cada clase debe tener responsabilidad sobre una sola parte de la funcionalidad proporcionada por el software, es decir, esta clase o modulo solo debe tener un único objetivo así como todos los elementos que la conforman, todas las funciones deben estar encapsuladas por la clase.

Su propósito es el de poder establecer elementos funcionales con objetivos únicos dentro del desarrollo encapsulando dicha funcionalidad para darle mayor flexibilidad al desarrollo ayudando también al proceso de pruebas unitarias las cuales permiten el testing de elementos específicos con responsabilidad única dentro del software.


# Características de un “buen código limpio”.

Desde mi punto de vista, un código “limpio” puede definirse en varios puntos:

    1. Una arquitectura bien definida. Es importante definir una arquitectura o combinación de arquitecturas según el tipo de desarrollo y proceso. Nos ayuda a mantener orden y estructura dentro de nuestro código.
    2. Basarnos en los principios de desarrollo SOLID considero una buena práctica, a veces es complicado poder integrar  en  programas estos principios, sin embargo, a medida que tratemos de acercarnos a SOLID estaremos programando un código cada vez mas claro y organizado: 
        a) Principio de responsabilidad única.
        b) Principio Open/Closed
        c) Principio de Sustitución de Liskov
        d) Principio de Segregación de Interfaces
        e) Principio de Inversión de Dependencias

Para mayor información recomiendo leer este blog:

https://devexperto.com/principio-responsabilidad-unica/

    3. Hacer pruebas unitarias de nuestros códigos nos ayuda a verificar el estado de nuestra codificación además de probar la funcionalidad de ese módulo. Cuando no podemos hacer pruebas unitarias es un indicador de que probablemente a nuestro código le falte mas claridad.
    
    
    
    
   # Descripción del proyecto.
   
El proyecto permite presentar los diferentes videos por tipo (Popular, TopRate y UpComing), permite ver el detalle de cada película seleccionada, funciona Online o bien OffLine. Tiene un buscador de películas Online y Offline.

El proyecto básicamente esta dividido por varias capas. Las capas principales son Presentador que incluye la vista y presenter, interactor (MVP model) y la capa de repositorios de datos (remoto y local). Se crean mas capas de uso como network para temas de coneccion, modelos de objetos (dto), custom dialogs, etc.

A nivel de Gradle se declararon 2 flavor (Develop y production) y 2 variantes de compilación (debug y release), la idea es permitir a la aplicación la creación de dos tipos de productos con sus variantes de compilación para la declaración de rutas de las API, un ambiente de desarrollo y uno productivo así como las variantes de compilación para temas de proguard, apk firmado, etc. Se declararon en el gradle a nivel de proyecto las extensiones de versiones que se usan en el gradle del módulo. El objetivo de declarar las extensiones es poder organizar y ordenar las versiones dando mas claridad en el cambio de alguna versión de las librerías usadas.


EL proyecto se comunica entre capas por medio de interfaces.

Se usa RX para la consulta de datos de servicios web por medio de Retrofit.

Se usa RX en el editText para el buscador con debounce a 800 milisegundos para la pausa al escribir el texto en el buscador.

Para la persistencia local se usa RealmDB.

Para el tema de animaciones se usa Lottie.

Para la inyección de referencias de vista se usa ButterKnife

Se usa Glide para descargar la imagen e incorporarla al ImageView control.

Para la descarga de imágenes se usa HttpURLConnection pero en clases que extienden de Runnable que después se implementan con ThreadPoolExecutor. La idea es permitir descarga de archivos múltiples de forma simultanea maneja la respuesta de las descargas a través del callback del Handler.


# Esta es la descripción de las clases:



Adapter:  	Paquete para las clases adaptadores del proyecto

Constant: 	Paquete para clase de constantes del proyecto.

Data:		     Paquete para clases de manejo de datos remoto, local y preferencias compartidas.

Domain:	Paquete para las clases que interactuan con los presentadores del proyecto. Aquí se crean las clases de la capa de Modelo (MVP)

Model:		Paquete para las clases de tipo DTO (data type object)

Platform:	Paquete para la plataforma del proyecto, aquí se crean las clases personalizadas como dialogos o loading, clases para inyeccion de dependencias con dagger, manejadores de errores, logs, network conection, descarga de archivos y servicios del proyecto.

Presentation:	Paquete para las clases de la vista, capa de presentación y clases base para las vistas.

Root:			Paquete para la clase que extiende de Application.

Util:				Paquete para clases de utileria de uso común para el proyecto. Clases para convertir datos, formatters, etc.




PRESENTATION 				DOMAIN                DATA

   View -----> Presenter------>Model (Interactor)----> Remote / Local




Descripción de clases:

SplashActivity:			Clase de inicio del proyecto. Presenta animación del logo con Lottie.

BaseActivity:		  	Clase que se usa como base de los activity. Define el tamaño de la pantalla (fullscreen) y visibilidad del teclado (on/off).



HomeActivity:		Activity principal del proyecto. Se declara ViewPager y TabLayout para la presentación de las películas por clasificación. Esta clase integra RX para la opción de búsqueda con debounce a 800  milisegundos. En esta clase se usa el adaptador para el viewpage e incorpora el buscador enviando la cadena de búsqueda como un query. Agrega los diferentes tabs con su fragment respectivo.


MovieContract:			Interface para las capas View y Presenter. Manejo de éxito en las transacciones, error y métodos de acceso.



PopularFragment:		Esta clase extiende de Fragment y es la vista que presenta el recyclerview de las películas. Incorpora la interfaz de la vista asi como crea la instancia del objeto PopularPresenter que es la capa presenter. El proyecto solo usa un fragment para los 3 tabs. En base a la posición del tab es el tipo de servicio que se solicita para obtener los datos. Esta clase permite también obtener los datos para la búsqueda de películas mediante un Query. Presenta Toast para el caso de los errores que envía el interactor o presenter.


PopularPresenter:		Clase que funciona como la capa de presentación en el modelo MVP. En esta clase se declara la instancia de la interfaz de la vista así como la instancia del modelo (clase interactor).


MovieInteractor:			Clase que funciona como la capa modelo en MVP. Esta clase declara la instancia de la interfaz del presentador así como instancias para los diferentes repositorios para obtener los datos ya sean remotos (webservice con retrofit) o locales (base de datos con RealmDB). Implementación de un método para el proceso de descarga de imagenes (multi files) usando ThreadPoolExecutor, la idea es la descarga de varias imágenes de forma simultanea. Solo se descargan las imagenes que no se encuentren en la base de datos local. Recibe los resultados de las descargas mediante un callback del Handler.


MoviesAdapter:			Clase que extiende de RecyclerView.Adapter<MoviesAdapter.ViewHolder>. Funciona como adaptador personalizado para el recyclerview que presenta las peliculas. Declara un ViewHolder para el manejo de los items del layout. Presenta la imagen con Glide (remoto o local) e incorpora el evento respectivo para presentar un Dialog Custom con el detalle de la película.


PagerAdapter:			Clase que extiende de FragmentPagerAdapter. Funciona como adaptador personalizado para el ViewPager de los tabs. Manejo de un arreglo de Fragments que se declaran en la vista.


Constant:				Clase para la definición de constantes. Declaración de tipos de películas, tipos de errores, entre otros.

ConstantService:	Clase para la definición de constantes para el tema de servicios. URL base de los servicios web, rutas de métodos de servicios que se acceden, api key, entre otros.  Esta clase esta en la rama de los flavor por lo que se ubica en el flavor de develop o production segun corresponda.


ApiConnect:			Clase que implementa el método de conexión para el cliente de retrofit.

ApiService:			Interfaz que define los métodos de acceso a los servicios web. Define el Header, Autorización, tipo de método, entre otros.

RetrofitClient:		Clase que implementa un método para obtener el cliente de retrofit, crea el interceptor para el tema de logs.

CinemaNicePreference:	Clase que da acceso a la implementación de las preferencias compartidas (PreferenceShared). Implementa métodos de set y get para los diferentes tipos de valores.


PopularRemote:			Clase que implementa los métodos de acceso a los diferentes servicios. Regresa suscripciones de RX para el manejo de las respuestas de los servicios.

DataRepository:		Clase que implementa los accesos a base de datos local con RealmDB. Métodos para agregar registros, consultar, actualizar y eliminar datos locales.


PopularResponse:		Clase de tipo DTO (Data Type Object) para la respuesta del servicio Popular Movies.

TopRatedResponse:		Clase de tipo DTO (Data Type Object) para la respuesta del servicio Top Rated Movies.

UpComingResponse:		Clase de tipo DTO (Data Type Object) para la respuesta del servicio UpComing Movies.

Dates:					    Clase de tipo DTO (Data Type Object) para la respuesta de los servicios Movies.
		
ResultMovie:		  	Clase de tipo DTO (Data Type Object) para la respuesta de los servicios de Movies.


DetailDialog:		    Clase que hereda de DialogFragment y tiene como objetivo presentar un dialogo modal con la descripción de la película.

LoadDialog:		      Clase que hereda de DialogFragment y tiene como objetivo presentar un dialogo modal con el loading para el tiempo de espera.


GridSpacingItemDecoration:	Clase que hereda de  RecyclerView.ItemDecoration para calcular los espacios entre cards cuando se llenan los registros del recyclerview.

CinemaNiceModule:		Clase que se usa para la creación de módulo para la inyección de dependencias en Dagger.

CinemaNiceComponent:		Interfaz en la que se definen los métodos que se inyectan para Dagger,


ApplicationScope:		Interfaz que define el Scope de inyección de dependencias para Dagger.


CinemaNiceErrorManager:		Clase para el manejo de mensaje de error de la aplicación.



LogMon:				Clase para el tratamiento de errores de la aplicación.

Conexion:				Clase para uso de temas relacionados con la conexión a internet. Implementa un método para verificar si hay conexión o no.

DownloadFile:			Clase que extiende de Runnable e implementa un método para la descarga de la imagen, verifica que la imagen no exista en la base de datos local, si no existe, la almacena en la base de datos local en array de bytes.


App:				Clase que extiende de MultiDexApplication, se ejecuta al inicio de arrancar la app, método para singleton, set de configuraciones para RealmDB, MultiDex para configuración de múltiples archivos DEX, show dialog del loading, entre otros.


ConverterGen:		Clase para la conversión de datos entre tipos.
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
