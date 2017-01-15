'use strict';

var serviceEntries = [];
var minRelevance = 0.5;

window.onload = function() {
    //hideSections();
    createServiceEntries(serviceEntries);
};

// we use $.ajax to load the diagram.
// make sure you run the application via web-server (ie. connect (node) or asdf (ruby))

// require the viewer, make sure you added the bpmn-js bower distribution
// along with all its dependencies to the web site
var BpmnViewer = window.BpmnJS;

var viewer = new BpmnViewer({  container: $('#canvas'), height: 600  });

var xhr = new XMLHttpRequest();

xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
        viewer.importXML(xhr.response, function(err) {

            if (!err) {
                console.log('success!');
                viewer.get('canvas').zoom('fit-viewport');
            } else {
                console.log('something went wrong:', err);
            }
        });
    }
};


function createServiceEntries(x) {
    x.push(new service("Get Customer data from database",              "http://localhost:8080/customer/get?customerId=1"));
    x.push(new service("Get Customer Data from database",          "http://localhost:8080/customer/getData?customerId=1"));
    x.push(new service("Get Customer Data from database",      "http://localhost:8080/customer/getByName?lastName=Muster&firstName=Max"));
    x.push(new service("Get Customer Name from database",         "http://localhost:8080/customer/getCustomerName?customerId=1"));
    x.push(new service("Get Customer Birthdate from database",    "http://localhost:8080/customer/getCustomerBirthdate?customerId=1"));
    x.push(new service("Get Customer Address from database",      "http://localhost:8080/customer/getCustomerAddress?customerId=1"));
    x.push(new service("Get data of all customers from database",         "http://localhost:8080/customer/getAll"));
    x.push(new service("Get data of all customers from database",              "http://localhost:8080/customer/getEveryone"));
    x.push(new service("Get table of all customers data from database", "http://localhost:8080/customer/getTableOfAllCustomers"));
    x.push(new service("Get data of peergroup from database",             "http://localhost:8080/customer/getPeerGroup?customerId=1"));
    x.push(new service("Get data of peergroup from database",   "http://localhost:8080/customer/PeerGroup?customerId=1"));
    x.push(new service("Get  table of peergroup data from database",    "http://localhost:8080/customer/PeerGroupTable?customerId=1"));
    x.push(new service("Calculate customer sales",  "http://localhost:8080/customer/sales?customerId=1"));
    x.push(new service("Calculate customer sales","http://localhost:8080/customer/businessVolume?customerId=1"));
    x.push(new service("Calculate customer sales by Name","http://localhost:8080/customer/salesByName?lastName=Muster&firstName=Max"));
    x.push(new service("Calculate aggregated sales of all customers","http://localhost:8080/customer/aggregratedSales"));
    x.push(new service("Calculate aggregated sales of all customers","http://localhost:8080/customer/allCustomersSalestotal"));
    x.push(new service("Calculate aggregated sales of all customers",             "http://localhost:8080/customer/allSales"));
    x.push(new service("Calculate aggregated sales of peergroup","http://localhost:8080/customer/aggregratedPeerGroupSales?customerId=1"));
    x.push(new service("Calculate aggregated sales of peergroup","http://localhost:8080/customer/PeerGroupSalestotal?customerId=1"));
    x.push(new service("Calculate aggregated sales of peergroup",            "http://localhost:8080/customer//PeerSales?customerId=1"));
    x.push(new service("Grant Discount",            "http://localhost:8080/discount/grant?customerId=1"));
    x.push(new service("Deny Discount",             "http://localhost:8080/discount/deny?customerId=1"));
    x.push(new service("Calculate Grant or Deny Discount",        "http://localhost:8080/discount/calculate?customerId=1"));
    x.push(new service("Calculate Grant or Deny Discount in Detail","http://localhost:8080/discount/calculateInDetail?customerId=1"));
    x.push(new service("Calculate Average customer sales",   "http://localhost:8080/discount/calculateAverageSales"));
}

function hideSections() {
    $( "#GetCustomerDataSection" ).hide();
    $( "#GetAllCustomerDataSection" ).hide();
    $( "#GetPeergroupDataSection" ).hide();
    $( "#GetCustomerSalesSection" ).hide();
    $( "#GetAllCustomersSalesSection" ).hide();
    $( "#GetPeergroupSalesSection" ).hide();
    $( "#CalculateDiscountQualificationSection" ).hide();
    $( "#GrantDiscountSection" ).hide();
    $( "#DenyDiscountSection" ).hide();
}


function service(name, url){
    return {name : name,
    url : url}
}

function fileUpload(){
    var x = document.getElementById("files");
    var file = x.files[0];
    xhr.open('GET', file.name, true);
    xhr.send(null);

    viewer.importXML(xhr.response, function(err) {

        if (!err) {
            console.log('success!');
            viewer.get('canvas').zoom('fit-viewport');
        } else {
            console.log('something went wrong:', err);
        }
    });
}

/* Returned values are strange atm, start in the middle? of the bpmn and returns several tasks again and again */
function analyze() {
    var taskArray = [];
    var attribArray = [];
    var taskName;
    var taskAttrib;

    // Parsing Part
    var elementRegistry = viewer.get('elementRegistry');
    elementRegistry.forEach(function(elem, gfx) {
        if (elem.businessObject.$instanceOf('bpmn:Task')){
            // do something with the task
            var taskType = elem.businessObject.$type;

            // and access to properties declared in the descriptor with
            taskName = elem.businessObject.get('name');
            addTask(taskName);
            taskArray.push(taskName+" ");



            // and to properties not declared in the descriptor with
            taskAttrib = elem.businessObject.$attrs;
            attribArray.pop(taskAttrib);
        }
    });


}

function addTask(task){
    //alert("debug 1");
    var services = getServices(task);
    var servicesHTML = generateHTML(services);
    var taskDiv = "<div><br /><br />"+task +"<br /> Services: <br />" +servicesHTML;
    document.getElementById("container").insertAdjacentHTML('beforeend', taskDiv);
}

function getServices(task){
    var relevantServices = [];
    for(var z = 0;z< serviceEntries.length;z++){
        var matches = 0;
        var splitName = serviceEntries[z].name.toLowerCase().split(" ");
        var splitTask  = task.toLowerCase().split(" ");
        for(var a = 0; a < splitTask.length;a++){
            if(splitName.includes(splitTask[a])){
                matches++;
            }
        }
        //(matches);
        var relevance = matches/(splitTask.length);
        if(relevance>=minRelevance){
            relevantServices.push(serviceEntries[z]);
        }
    }
    return relevantServices;

}

function generateHTML(serviceList){
    //alert(serviceList.length);
    var precision = (serviceList.length)/(serviceEntries.length);
    var recall;
    var fmeasure;
    var html = "";
    for(var service = 0; service < serviceList.length;service++){
        html = html + "" + serviceList[service].name + ":  <a href=" + serviceList[service].url+ "> "+ serviceList[service].url +"</a> precision: " + precision;
        html = html + " <br />";
    }
    return html;
}