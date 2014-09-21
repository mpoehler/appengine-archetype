
    var listAllPersons = function() {

        /* clean list div element */
        $("#list").empty();

        /* fetch all Persons from server and add them to the list */
        $.ajax({
            type: "GET",
            url: "/rest/person/fetchAll",
            success: function(data) {
                console.log("Data from server: " + data);
                $.each(data, function(index, value) {
                    console.log('Name: ' + value.name + ', Id: ' + value.id);
                    $('#list').append('<li>'+value.name+' <a href="#" data-id="'+value.id+'">X</a></li>');
                });
                $("#list a").click(deleteItem);
            },
            error: function() { console.log("Ups. Something went wrong.") }
        });
    };

    var deleteItem = function(event) {
        console.log('Delete this item. ID:' + $(event.target).attr("data-id"));
        $.ajax({
            type: "DELETE",
            url: "/rest/person/"+$(event.target).attr("data-id"),
            success: function() {
                console.log("Person deleted.")
                listAllPersons();
            },
            error: function(xmlHttpRequest, textStatus, errorThrown) {
                console.log("Ups. Something went wrong.");
                alert(errorThrown);
            }
        });
        return false;
    };

$( document ).ready(function() {

    /* add a handler for the input box */
    $("#addButton").click(function() {
        console.log("call the rest service and add the value " + $("#name").val());

        var data = {'name': $("#name").val() };
        $.ajax({
            type: "POST",
            url: "/rest/person",
            data: JSON.stringify(data),
            dataType:'json',
            contentType:'application/json',
            success: function() {
                console.log("Person saved.");
                listAllPersons();
            },
            error: function() { console.log("Ups. Something went wrong.") }
        });
    });

});
