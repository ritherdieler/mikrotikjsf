$(document).ready(function(){

/*var $s21 = $( "#pg" ).text();
alert($s21);*/


 
   var $count=0;
   var $suma=;
   var $average =0;
$( ".infopanel" ).on('DOMSubtreeModified',function() {
var $ping=$(".ui-outputlabel").text();

if($ping!=0)
{
  // hay problemas aqui porque esto actualiza el componente y la funcion de arriba se vuelve a llamar
  /*$('#av').text($average);*/
  $count++;
  $suma=parseInt($suma)+parseInt($ping);
  
  $average=Math.round(($suma/$count),2);
   console.log($suma);
    console.log($count);
   console.log($average);

}

   if (($ping>0)&&($ping<=20)) {

    $(this).css('background-color', '#60D835');

   }
    else if(($ping>20)&&($ping<=50)){
      $(this).css('background-color', 'orange');
    }

    else{
      $(this).css('background-color', 'red');
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

 


