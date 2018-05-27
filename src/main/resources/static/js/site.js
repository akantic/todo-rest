function getTodos(options) {
    $.ajax({
        type: 'GET',
        url: '/todos',
        data: options,
        dataType: 'json',
        success: function(data) {
            loadTodoTable(data, options);
        }
    });
}

function updateTodo(todo) {
    $.ajax({
        type: 'PUT',
        url: '/todos/' + todo.id,
        data: JSON.stringify(todo),
        contentType: 'application/json',
        dataType: 'json',
        success: function() {
            getTodos({completed: todo.completed})
        }
    });
}

function addNewTodo() {
    let labelHolder = document.getElementById("label-holder");
    let labels = [];
    if (labelHolder != null) {
        for (l of labelHolder.children) {
            for (c of l.children) {
                if (c.className === "label-id") {
                    labels.push({id: c.value});
                }
            }
        }
    }
    let todo = {
        description: document.getElementById("form-description").value,
        dueDate: document.getElementById("form-duedate").value,
        labels: labels
    };

    let errorDiv = document.getElementById("todo-errors");
    errorDiv.innerHTML = "";
    let errors = false;
    // Validation
    if (!todo.description || todo.description.length < 3) {
        console.log("Invalid description");
        errorDiv.innerHTML += `<div class="todo-error">Description should be 3-100 characters long.</div>`
        errors = true;
    }
    if (!todo.dueDate || new Date(todo.dueDate) < new Date()){
        console.log("Invalid date");
        errorDiv.innerHTML += `<div class="todo-error">Date is invalid.</div>`
        errors = true;
    }
    if (todo.labels.length > 3) {
        console.log("Too many labels");
        errorDiv.innerHTML += `<div class="todo-error">Max number of labels is 3.</div>`
        errors = true;
    }
    if (errors) return;
    $.ajax({
        type: 'POST',
        url: '/todos',
        contentType: 'application/json',
        data: JSON.stringify(todo),
        success: function(data) {
            hideAddNewTodoModal();
            getTodos({completed: false});
        },
        failure: function(data) {
            errorDiv.innerHTML += `<div class="todo-error">Invalid request</div>`
        }
    });
}

function deleteTodo(id, view) {
    $.ajax({
        type: 'DELETE',
        url: '/todos/' + id,
        success: function() {
            getTodos({completed: view})
        }
    });
}

function getStarted() {
    getTodos({completed: false});

    document.getElementById("main").innerHTML +=
        `<div id="buttons">
            <div id="switch-todos">
            </div>
            <div id="new-todo">
                <button onclick='showAddNewTodoModal()' class="btn btn">New Todo item</button>
            </div>
        </div>`;
}

function showAddNewTodoModal() {
    document.getElementById("form-description").value = "";
    document.getElementById("form-duedate").valueAsDate = new Date((new Date()).getTime() + 7*24*60*60*1000);
    document.getElementById("new-todo-modal").style.display = "block";
    document.getElementById("selected-labels").innerHTML = "";
    document.getElementById("todo-errors").innerHTML = "";
}

function showLabelList() {
    $.ajax({
        type: 'GET',
        url: '/labels',
        dataType: 'json',
        success: function(data) {
            let labelList = document.getElementById("label-list");
            labelList.innerHTML = "";
            for (label of data) {
                labelList.innerHTML +=
                    `<div>
                        <a href="#" onclick='addLabelToTodo(${JSON.stringify(label)});'>${label.value}</a>
                        <button class="btn btn-danger btn-del-label" onclick='deleteLabel(${JSON.stringify(label)});'>&Cross;</button>
                    </div>`;

            }
            labelList.classList.toggle("show");
            window.onclick = function(event) {
                if (!event.target.matches('.dropbtn')) {
                    if (document.getElementById("label-list").classList.contains("show")) {
                        document.getElementById("label-list").classList.toggle("show");
                        window.onclick = undefined;
                    }
                }
            };
        }
    });

}

function addNewLabel() {
    let labelValue = document.getElementById("form-labelname").value.trim();
    if (!labelValue || labelValue.length > 10) {
        return;
    }
    let label = {"value" : labelValue};
    $.ajax({
        type: 'POST',
        url: '/labels',
        contentType: 'application/json',
        data: JSON.stringify(label),
        success: function(data) {
            document.getElementById("form-labelname").value = "";
            addLabelToTodo(data);
        }
    });
}

function addLabelToTodo(label) {
    let labelList = document.getElementById("selected-labels");
    if (labelList.children.length === 0) {
        labelList.innerHTML = `<span>Selected labels: </span><div id="label-holder"></div>`;
    }
    let holder = document.getElementById("label-holder");
    for (l of holder.children) {
        if (parseInt(l.children[0].value) === label.id) {
            return;
        }
    }
    holder.innerHTML +=
        `<div class="label-container">
            <input type="text" value="${label.id}" hidden class="label-id">
            <span class="badge badge-info label-badge">${label.value.toUpperCase()}</span>
            <button class="btn btn-danger btn-rmv-label" onclick='removeLabel(${JSON.stringify(label)});'>&Cross;</button>
        </div>`;
}

function deleteLabel(label) {
    $.ajax({
        type: 'DELETE',
        url: '/labels/' + label.id,
        success: function() {
            removeLabel(label);
        },
        failure: function() {
            console.log("HI");
        }
    });

}

function removeLabel(label) {
    let holder = document.getElementById("label-holder");
    if (!holder) return;
    for (l of holder.children) {
        if (parseInt(l.children[0].value) === label.id){
            holder.removeChild(l);
        }
    }
    if (holder.children.length === 0) {
        document.getElementById("selected-labels").innerHTML = "";
    }
}


function hideAddNewTodoModal() {
    document.getElementById("new-todo-modal").style.display = "none";
}

function loadTodoTable(data, options) {
    mainContainer = document.getElementById("todo-container");
    let date = options.completed ? "Completed At" : "Due Date";
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
                    <th>Labels</th>
                    <th>${date}</th>
                    <th></th>
                </tr>
            </table>`;
    }

    for (todo of data) {
        if (options.completed && todo.completed || !options.completed && !todo.completed)
            addTodoToTable(todo);
    }

    if (options.completed) {
        document.getElementById("switch-todos").innerHTML =
            `<button onclick='let opt = { completed: false }; getTodos(opt);' class="btn btn">Show active todos</button>`;
    } else {
        document.getElementById("switch-todos").innerHTML =
            `<button onclick='let opt = { completed: true }; getTodos(opt);' class="btn btn">Show completed todos</button>`;
    }
}


function markTodoAsComplete(id) {
    updateTodo({id: id, completed: true});
}

function addTodoToTable(todo) {
    let table = document.getElementById("todo-table");
    let newRow = table.insertRow();
    newRow.insertCell().appendChild(document.createTextNode(todo.description));
    let labels = document.createElement("div");
    labels.className = "label-display-row"
    if (todo.labels.length > 0) {
        for (l of todo.labels) {
            labels.innerHTML += `<span class="badge badge-info label-badge">${l.value.toUpperCase()}</span>`;
        }

    } else {
        labels.innerHTML = "-";
    }
    newRow.insertCell().appendChild(labels);
    if (!todo.completed) {
        let dueDate = todo.dueDate.split("T")[0];
        let daysLeft = Math.floor((new Date(dueDate).getTime() - new Date().getTime()) / (24*60*60*1000));
        dueDate = dueDate.split("-");
        let formattedDate = dueDate[1] + "-" + dueDate[2] + "-" + dueDate[0];
        if (daysLeft > 0) {
            newRow.insertCell().innerHTML = `${formattedDate} <span class="date-alert">(in ${daysLeft} days)</span>`
        } else {
            newRow.insertCell().innerHTML = `${formattedDate} <span class="date-alert">(${Math.abs(daysLeft)} days ago)</span>`
        }
        newRow.insertCell().innerHTML = `
            <button onclick="markTodoAsComplete(${todo.id});" class="btn btn-success">
                <i class="fas fa-check-circle"></i>
            </button> 
            <button onclick="deleteTodo(${todo.id}, ${todo.completed});" class="btn btn-danger">
                <i class="fas fa-times-circle"></i>
            </button>`;
    } else {
        let dateCompleted = todo.completedAt.split("T")[0].split("-");
        let formattedDate = dateCompleted[1] + "-" + dateCompleted[2] + "-" + dateCompleted[0];
        newRow.insertCell().innerHTML = `${formattedDate}`
        newRow.insertCell().innerHTML = `
            <button onclick="deleteTodo(${todo.id}, ${todo.completed});" class="btn btn-danger">
                <i class="fas fa-times-circle"></i>
            </button>`;
    }
}


