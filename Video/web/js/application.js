function buttonEffect( uuid )
{
    $("#"+uuid).hover
    (
        // Mouse Over
        function () 
        {
            $(this).animate({width: 95, height: 95}, 400);
        },
        // Mouse Out
        function () 
        {
            $(this).animate({width: 88, height: 88}, 300);
        }
    );
}

function redirectReport( json )
{
    var myForm = document.createElement( "form" );
    myForm.method= "post";
    myForm.action = contextPath + "/report";
    myForm.target = "_blank";
    
    var myInput = document.createElement( "input" );
    myInput.setAttribute( "name", "data" );
    myInput.setAttribute( "value", json );
    
    myForm.appendChild( myInput );
    
    document.body.appendChild( myForm );
    myForm.submit();
    document.body.removeChild( myForm );
}