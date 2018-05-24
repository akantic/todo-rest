
function getTodos() {
    $.ajax({
        type: 'GET',
        url: '/todos',
        dataType: 'json',
        success: function(data) {
            loadTodoTable(data);
        }
    });
}

function addNewTodo() {

}


function loadTodoTable(data) {
    mainContainer = document.getElementById('main');
    if (data.length < 1) {
        mainContainer.innerHTML =
            "<div class='no-items'>" +
            "There are no Todo items in the database yet, why don't you create some?" +
            "</div>";
    } else {

    }

    mainContainer.innerHTML +=
        "<div class='new-todo'>" +
        "<button onclick='addNewTodo()' class='btn btn'>New Todo item</button>" +
        "</div>";
}