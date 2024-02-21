# Articles from New York Times
Prueba tecnica 

**Objetivo Genneral:**
Nuestro objetivo es evaluar tus habilidades para consumir una API REST en Android y
presentar los datos de manera efectiva en la interfaz de usuario. Además, valoraremos
tus buenas prácticas de codificación, manejo de errores y la implementación de la
persistencia de datos. También te pedimos que te familiarices con la estructura y
arquitectura del proyecto que te proporcionaremos.

**Api New York Times Articles**
url documentación: https://developer.nytimes.com/docs/most-popular-product/1/overview

Para este reto solo se crearon 2 fragmentos dentro del feature home, los cuales el principal es el listado de los articulos obtenidos del microservicio, y el segundo fragmento hace referencia a los detalles de cada articulo obtenidos del microservicio.
Ambos solo cuentan con los colores blanco y negro para seguir la estetica de un periodico por exepcion del status bar.
Para el diseño se opto por un listado recomendado por Google, donde los elementos se muestran en CardsView con interacciones, dando solo un resumen del titulo, el abstract y la imagen del articulo, en caso de no contener imagen, se muestra una generica.

HomeFragment:   

<img width="515" alt="image" src="https://github.com/DGOSalazar/news/assets/99159921/a5b57edc-f31c-4fe6-a3ed-d414290bf663">

Para la pantalla de detalle, se obto por seguir la estetica de la pagina web, haciendo alucion a una portada de periodico, en esta imagen tomamos la de mayo resolución.
DetailFragment:

<img width="515" alt="image" src="https://github.com/DGOSalazar/news/assets/99159921/7bafc4a3-1b4e-4329-ade0-fd8eebdabbce">


De igual manera se realizo la implementacion de una base de datos con Room para almacenar lo obtenio del api, la cual se consume la primera vez que se abre la app, y se almacenan hasta que la app se desinstala.
Finalmente se generaron pruebas unitarias para el Repositorio y el caso de uso principal.



