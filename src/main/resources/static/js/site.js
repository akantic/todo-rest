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
    let todo = {
        description: document.getElementById("form-description").value
    };

    // Validation
    if (!todo.description) {
        return;
    }

    console.log(todo);
    $.ajax({
        type: 'POST',
        url: '/todos',
        contentType: 'application/json',
        data: JSON.stringify(todo),
        success: function(data, textStatus, req) {
            hideAddNewTodoModal();
            addTodoToTable(data);
            document.getElementById("form-description").value = '';
        }
    });
}

function getStarted() {
    document.getElementById("main").innerHTML +=
        `<div class="new-todo">
            <button onclick='showAddNewTodoModal()' class="btn btn">New Todo item</button>
        </div>`;
    getTodos();
}

function showAddNewTodoModal() {
    document.getElementById("new-todo-modal").style.display = "block";
}

function hideAddNewTodoModal() {
    document.getElementById("new-todo-modal").style.display = "none";
}

function loadTodoTable(data) {
    mainContainer = document.getElementById("todo-container");
    if (data.length < 1) {
        mainContainer.innerHTML =
            `<div class="no-items">
                There are no Todo items of this type yet.
            </div>`;
    } else {
        mainContainer.innerHTML =
            `<table id="todo-table" class="table">
                <tr>
                    <th>Description</th>
                    <th>Due date</th>
                    <th></th>
                </tr>
            </table>`;
    }

    for (todo of data) {
        addTodoToTable(todo);
    }
}

function addTodoToTable(todo) {
    let table = document.getElementById("todo-table");
    let newRow = table.insertRow();
    newRow.insertCell().appendChild(document.createTextNode(todo.description));
    newRow.insertCell().appendChild(document.createTextNode(todo.createdAt));
    newRow.insertCell().innerHTML = `
        <button onclick="markTodoAsComplete()" class="btn btn-success">
            <i class="fas fa-check-circle"></i>
        </button>
        <button onclick="markTodoAsComplete()" class="btn btn-danger">
            <i class="fas fa-times-circle"></i>
        </button>`;
}