let formList = document.getElementById("planeId");


formList.innerHTML='';
        fetch("http://localhost:8000/planes/plane-list").
        then(response => response.json()).
        then(json => json.forEach(plane => {
            let currenNumber = '<tr class="rowOption">\n'
            currenNumber += `<td class="col-md-8"  *{planeNumber}></td>\n`
            currenNumber += '</tr>\n'
            formList.innerHTML+=currenNumber;
        }))
