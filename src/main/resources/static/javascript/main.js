const cookieArr = document.cookie.split("=")
const userId = Number(cookieArr[1]);

let createBugForm = document.getElementById("create-bug-form")
let name = document.getElementById('name')
let description = document.getElementById('description')
let taggedUserId = document.getElementById('taggedUserId')

//const noteContainer = document.getElementById("bug-container")
const bugTableBody = document.getElementById("bug-table-body")

let updateBody = document.getElementById("update-bug-body")
let updateBugBtn = document.getElementById('update-bug-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/bugs/"

const handleSubmit = async (e) => {
    e.preventDefault()
   let bodyObj = {
          name: name.value,
          description: description.value,
          taggedUserId: taggedUserId.value
      }
    await createBug(bodyObj);
    name.value = '';
    description.value = '';
    taggedUserId.value = '';
}

async function createBug(bodyObj) {
        await fetch(`${baseUrl}create/${userId}`, {
           method: "POST",
           body: JSON.stringify(bodyObj),
           headers: headers
       })
               .then(() => getBugs(userId))
           .catch(err => console.error(err.message))


}

async function getBugs(userId) {
    await fetch(`${baseUrl}bug/${userId}/all`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createBugTable(data))
        .catch(err => console.error(err))
}


async function getBugById(bugId){
    await fetch(`${baseUrl}bug/${bugId}`, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}
async function resolveBug(bugId){
    await fetch(`${baseUrl}${bugId}/resolve/${userId}`, {
        method: "PUT",
        headers: headers
    })
        .then(() => getBugs(userId))
        .catch(err => console.error(err.message))
}

async function handleBugEdit(bugId){
    let bodyObj = {
        taggedUserId: Number(updateBody.value)
    }

    await fetch(`${baseUrl}${bugId}/update/${userId}`, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
   .then(() => getBugs(userId))
   .catch(err => console.error(err))

}

const createBugTable = (array) => {

    bugTableBody.innerHTML = ''
    array.sort((a,b) => a.id -b.id).forEach(obj => {
    let row =  `
        <th scope="row">${obj.id}</th>
        <td>${obj.name}</td>
         <td>${obj.description}</td>
          <td>${obj.status}</td>
         <td>${obj.createdById}</td>
         <td>${obj.taggedUserId}</td>
             `
        if(obj.createdById === userId)  {
        row = row + `<button onclick="getBugById(${obj.id})" type="button" class="btn"
                      data-bs-toggle="modal" data-bs-target="#bug-edit-modal"> Edit </button>`
        }
         if(obj.createdById != userId && obj.status === "OPEN")  {
                row = row + `<button onclick="resolveBug(${obj.id})" type="button" class="btn"> resolve </button>`
                }

        let bugRow = document.createElement("tr")
        bugRow.innerHTML = row
        bugTableBody.append(bugRow);
    })
}
function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const populateModal = (obj) =>{
    updateBody.value = 0
    updateBody.value = obj.taggedUserId
    updateBugBtn.setAttribute('data-bug-id', obj.id)
}

getBugs(userId);

updateBugBtn.addEventListener("click", (e)=>{
    let bugId = e.target.getAttribute('data-bug-id')
    handleBugEdit(bugId);
})


createBugForm.addEventListener("submit", handleSubmit)

