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