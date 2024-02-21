
//Подключение к базе данных:
// const mysql = require("mysql2");
// const connection = mysql.createConnection({
//     host: 'localhost:8080',
//     user: 'root',
//     password: 'Guantanama20237654!',
//     database: 'jdbc:mysql://localhost:3306/mydbtest2'
// });
// connection.connect();

const innerContainer = document.querySelector('.inner')
const btn = document.querySelector('.btn')
const api = "http://localhost:8080/admin"


async function showFriendsList() {
    try {
        const response = await fetch(api, {
            mode: 'no-cors'
        })

        if (response.ok) {
            const data = await response.json()
            createCards(data)
        } else {
            console.log("Error HTTP: " + response.status)
        }
    } catch (error) {
        console.log("Ошибка при выполнении запроса: " + error.message)
    }
}

showFriendsList()

function createCards(cardsData) {
    cardsData.forEach(cardData => {
        const card =
            `<div class="card">
                
                <div class="card_name">${cardData.username}</div>
                <div class="card_email">${cardData.email}</div>
                
            </div>`
        innerContainer.insertAdjacentHTML('beforeEnd',card)
    })
}

btn.addEventListener('click', function () {
    if (innerContainer.childElementCount>0) {
        innerContainer.innerHTML = ''
    } else {
        showFriendsList()
    }

})


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