$(async function () {
    await getAuthUser();
    await getAllUsers();
    await newUser();
    await removeUser();
    await updateUser();

})

function getDomain() {
    return "http://localhost:8080";
}

async function post(url = "", data = {}) {
    const response = await fetch(getDomain() + url, {
        method: "POST",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
        },
        redirect: "follow",
        referrerPolicy: "no-referrer",
        body: JSON.stringify(data),
    });
    return response.json();
}

async function get(url = "") {
    const response = await fetch(getDomain() + url, {
        method: "GET",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
        },
        redirect: "follow",
        referrerPolicy: "no-referrer"
    });
    return response.json();
}

async function getAuthUser() {
    await get("/api/users/user")
        .then(data => {
            console.log(data);
            document.querySelector('#userName').textContent = data.username;
            document.querySelector('#userRole').textContent = (data.roles.map(role => " " + role.name.substring(5)).join(' '));

            var userDataInfo = document.getElementById('userDataInfo');
            if (userDataInfo == null) {
                return;
            }
            while (userDataInfo.firstChild) {
                userDataInfo.removeChild(userDataInfo.firstChild);
            }

            var tr = document.createElement('tr');
            userDataInfo.append(tr);

            var id = document.createElement('td');
            tr.appendChild(id);
            id.textContent = data.id;

            var username = document.createElement('td');
            tr.appendChild(username);
            username.textContent = data.username;

            var email = document.createElement('td');
            tr.appendChild(email);
            email.textContent = data.email;

            var roles = document.createElement('td');
            tr.appendChild(roles);
            roles.textContent = data.roles.map(ob => ob.name).join(' ');
        })
        .catch(error => console.log(error))
}

async function getAllUsers() {
    await get("/admin/all")
        .then(data => {
            console.log(data);

            var adminDataInfo = document.getElementById('adminDataInfo');
            if (adminDataInfo == null) {
                return;
            }
            while (adminDataInfo.firstChild) {
                adminDataInfo.removeChild(adminDataInfo.firstChild);
            }
            data.forEach((elem) => {
                var tr = document.createElement('tr');
                adminDataInfo.append(tr);

                var id = document.createElement('td');
                tr.appendChild(id);
                id.textContent = elem.id;

                var username = document.createElement('td');
                tr.appendChild(username);
                username.textContent = elem.username;

                var email = document.createElement('td');
                tr.appendChild(email);
                email.textContent = elem.email;

                var roles = document.createElement('td');
                tr.appendChild(roles);
                roles.textContent = elem.roles.map(ob => ob.name).join(' ');

                var edit = document.createElement('td');
                tr.appendChild(edit);
                edit.innerHTML = '<button type="button" class="btn btn-info" data-bs-toggle="modal" data-id="' + elem.id + '" data-bs-target="#edit">Edit</button>'

                var del = document.createElement('td');
                tr.appendChild(del);
                del.innerHTML = '<button type="button" class="btn btn-danger" data-bs-toggle="modal" data-id="' + elem.id + '" data-bs-target="#delete">Delete</button>'
                del.onclick = function () {
                    showDeleteModal(elem.id);
                };

            });
        })
        .catch(error => console.log(error))
}


async function showDeleteModal(id) {
    let user = await getUser(id)
    const form = document.forms["deleteForm"];

    form.idDeleteUser.value = user.id;
    form.usernameDeleteUser.value = user.username;
    // form.lastNameDeleteUser.value = user.lastName;
    form.emailDeleteUser.value = user.email;


    $('#rolesDeleteUser').empty();

    user.roles.forEach(role => {
        let el = document.createElement("option");
        el.text = role.name.substring(5);
        el.value = role.id;
        $('#rolesDeleteUser')[0].appendChild(el);
    });

}

$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function getUser(id) {
    let response = await fetch("/admin/" + id);
    return await response.json();
}

async function newUser() {
    fetch("/admin/roles")
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option")
                el.value = role.id
                el.text = role.name.substring(5)
                $('#rolesNew')[0].appendChild(el)
            })
        }).catch(error => console.log(error))


    const createForm = document.forms["createForm"]
    const createLink = document.querySelector('#addNewUser')
    const createButton = document.querySelector('#createUserButton')



    createLink.addEventListener('click', (event) => {
        event.preventDefault()
        createForm.style.display = 'block'
        console.log("createForm.style.display = 'block'")
    })
    createForm.addEventListener('submit', addNewUser)
    createButton.addEventListener('click', addNewUser)

    async function addNewUser(e) {
        e.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < createForm.role.options.length; i++) {
            if (createForm.role.options[i].selected) newUserRoles.push({
                id: createForm.role.options[i].value,
                roles: createForm.role.options[i].text
            })
        }

        fetch("/admin", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: createForm.username.value,
                password: createForm.password.value,
                email: createForm.email.value,
                roles: newUserRoles
            })
        }).then(() => {
            createForm.reset();

            $(async function () {
                console.log("await getAllUsers()")
                await getAllUsers()
            })
        })
    }
}

// Delete


$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget)
    let id = button.data('id')
    showDeleteModal(id)
})

// async function getUser(id) {
//     let response = await fetch("/admin/" + id)
//     return await response.json()
// }


// async function showDeleteModal(id) {
//     let user = await getUser(id)
//     const form = document.forms["deleteForm"]
//
//     form.idDeleteUser.value = user.id
//     form.usernameDeleteUser.value = user.username
//     form.emailDeleteUser = user.email
//
//     $('#rolesDeleteUser').empty()
//
//     user.roles.forEach(role => {
//         let el = document.createElement('option')
//         el.text = role.name.substring(5)
//         el.value = role.id
//         $('#rolesDeleteUser')[0].appendChild(el)
//     })
// }

$('#deleteUserButton').click(() => {
    removeUser()
})

async function removeUser() {
    const deleteForm = document.forms["deleteForm"]
    const id = deleteForm.idDeleteUser.value

    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault()
        fetch("/admin/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            getAllUsers()
            $('#topCloseButtonDelete').click()
        }).catch(error => console.log(error))
    })
}

//Edit


$('#edit').on('show.bs.modal', (ev) => {
    let button = $(ev.relatedTarget)
    let id = button.data('id')
    showEditModal(id)
})


async function showEditModal(id) {
    let user = await getUser(id)
    const form = document.forms["editForm"]

    form.idEditUser.value = user.id
    form.usernameEditUser.value = user.username
    form.passwordEditUser.value = user.password
    form.emailEditUser.value = user.email

    $('#rolesEditUser').empty()
    fetch("/admin/roles")
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option")
                el.value = role.id
                el.text = role.name.substring(5)
                $('#rolesEditUser')[0].appendChild(el)
            })
        })
}

$('#editUserButton').click(() => {
    updateUser()
})


async function updateUser() {
    const editForm = document.forms["editForm"]
    const id = editForm.idEditUser.value

    editform.addEventListener("submit", async (ev) => {
        ev.preventDefault()
        let editUserRoles = []
        for (let i = 0; i < editForm.rolesEditUser.options.length; i++) {
            if (editForm.rolesEditUser.options[i].selected) editUserRoles.push({
                id: editForm.rolesEditUser.options[i].value,
                role: editForm.rolesEditUser.options[i].text
            })
        }

        fetch("/admin/" + id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id : id,
                username : editForm.username.value,
                password : editForm.passwordEditUser.value,
                email : editForm.email.value,
                roles : editUserRoles
            }),
        })
            .then(() => {
                getAllUsers()
                $('#editFormCloseButton').click()
            })

    })
}

//window.onload = function() {
//    getAuthUser();
//
//    var url = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
//    if (url == "admin") {
//        getAllUsers();
//
//
////        await newUser();
////        await removeUser();
////        await updateUser();
//    }
//}


//
// //Подключение к базе данных:
// // const mysql = require("mysql2");
// // const connection = mysql.createConnection({
// //     host: 'localhost:8080',
// //     user: 'root',
// //     password: 'Guantanama20237654!',
// //     database: 'jdbc:mysql://localhost:3306/mydbtest2'
// // });
// // connection.connect();
//
// const innerContainer = document.querySelector('.inner')
// const btn = document.querySelector('.btn')
// const api = "http://localhost:8080/admin"
//
//
// async function showFriendsList() {
//     try {
//         const response = await fetch(api, {
//             mode: 'cors',
//             headers: {
//                 'Access-Control-Allow-Origin':'*'
//             }
//         })
//
//         if (response.ok) {
//             const data = await response.json()
//             createCards(data)
//         } else {
//             console.log("Error HTTP: " + response.status)
//         }
//     } catch (error) {
//         console.log("Ошибка при выполнении запроса: " + error.message)
//     }
// }
//
// showFriendsList()
//
// function createCards(cardsData) {
//     cardsData.forEach(cardData => {
//         const card =
//             `<div class="card">
//
//                 <div class="card_name">${cardData.username}</div>
//                 <div class="card_email">${cardData.email}</div>
//
//             </div>`
//         innerContainer.insertAdjacentHTML('beforeEnd',card)
//     })
// }
//
// btn.addEventListener('click', function () {
//     if (innerContainer.childElementCount>0) {
//         innerContainer.innerHTML = ''
//     } else {
//         showFriendsList()
//     }
//
// })
//


// let promise = fetch(url)
//
// let response = await fetch(url);
//
// if (response.ok) { // если HTTP-статус в диапазоне 200-299
//                    // получаем тело ответа (см. про этот метод ниже)
//     let json = await response.json();
// } else {
//     alert("Ошибка HTTP: " + response.status);
// }
//
//
// //Выполнение запросов к базе данных:
// connection.query('SELECT * FROM users', function (err, results, fields) {
//     if (err) throw err;
//     console.log(results);
// });
//
//
// //Выполнение запросов к базе данных:
// connection.query('SELECT * FROM users', function (err, results, fields) {
//     if (err) throw err;
//     console.log(results);
// });
//
// //Закрытие соединения с базой данных:
// connection.end();