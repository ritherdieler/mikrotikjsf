
$(document).ready(function(){
    console.log("iniciando js");
    var $count=0;
    var $suma=parseInt(0);
    var $average =0;

    $('.infopanelBody').on('DOMSubtreeModified',function() {
        var $ping= parseInt($(this).text(),10);
        var $parent = $(this);

        if($ping!=0)
        {
              // hay problemas aqui porque esto actualiza el componente y la funcion de arriba se vuelve a llamar

              $count++;
              $suma=parseInt($suma)+parseInt($ping);
              $average=Math.round(($suma/$count),2);
              console.log($suma);
              console.log($count);
              console.log($average);
              $('#av').text($average);
        }

       if (($ping>=0)&&($ping<=20)) {
            console.log("Color Verde");
            $parent.css('background-color', '#60D835');
       }else if(($ping>20)&&($ping<=50)){
           console.log("Color Naranja");
           $parent.css('background-color', 'orange');
       }else if($ping>50){
           console.log("Color Rojo");
           $parent.css('background-color', 'red');
       }

  })


 $('.menusup').click(function(e){

    var $li = $(this).parent();

   if($li.hasClass('activado'))
    {
      $li.removeClass('activado');
      $li.children('ul').slideUp();

    }

    else{
      console.log("activo");
      $('.menu li ul').slideUp();
      $('.menu li').removeClass('activado');
      $li.addClass('activado');
      $li.children('ul').slideDown();
        }
  });
  });

/*
function CambioColor(cuadro,ping){
    console.log("Funcion Cambio color");

    var $ping= parseInt(ping,10);
    if (($ping>=0)&&($ping<=20)) {
        console.log("Color Verde");
        cuadro.css('background-color', '#60D835');
    }else if(($ping>20)&&($ping<=50)){
        console.log("Color Naranja");
        cuadro.css('background-color', 'orange');
    }else if($ping>50){
        console.log("Color Rojo");
        cuadro.css('background-color', 'red');
        //$parent.css('background-color', 'red');
    }
}
*/


