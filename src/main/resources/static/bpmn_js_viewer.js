'use strict';

window.onload = function() {
    hideSections();
};

// we use $.ajax to load the diagram.
// make sure you run the application via web-server (ie. connect (node) or asdf (ruby))

// require the viewer, make sure you added the bpmn-js bower distribution
// along with all its dependencies to the web site
var BpmnViewer = window.BpmnJS;

var viewer = new BpmnViewer({ container: '#canvas' });

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

xhr.open('GET', 'IGT Order-Process Discount.bpmn', true);
xhr.send(null);



function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object

    // files is a FileList of File objects. List some properties.
    var output = [];
    for (var i = 0, f; f = files[i]; i++) {
        output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a', ') - ',
            f.size, ' bytes, last modified: ',
            f.lastModifiedDate.toLocaleDateString(), '</li>');
    }
    document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

document.getElementById('files').addEventListener('change', handleFileSelect, false);





function hideSections() {
    $( "#GetCustomerData" ).hide();
    $( "#GetAllCustomerData" ).hide();
    $( "#GetPeergroupData" ).hide();
    $( "#GetCustomerSales" ).hide();
    $( "#GetAllCustomersSales" ).hide();
    $( "#GetPeergroupSales" ).hide();
    $( "#CalculateDiscountQualification" ).hide();
    $( "#GrantDiscount" ).hide();
    $( "#DenyDiscount" ).hide();
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
            taskArray.push(taskName+" ");

            if(taskName.includes("Get customer data from database")){
                $( "#GetCustomerData" ).show();
            }
            if(taskName.includes("Get data of all customers from database")){
                $( "#GetAllCustomerData" ).show();
            }
            if(taskName.includes("Get data of peergroup from database")){
                $( "#GetPeergroupData" ).show();
            }
            if(taskName.includes("Calculate customer sales")){
                $( "#GetCustomerSales" ).show();
            }
            if(taskName.includes("Calculate aggregated sales of all customers")){
                $( "#GetAllCustomersSales" ).show();
            }
            if(taskName.includes("Calculate aggregated sales of peergroup")){
                $( "#GetPeergroupSales" ).show();
            }
            if(taskName.includes("Get data of peergroup from database")){
                $( "#CalculateDiscountQualification" ).show();
            }
            if(taskName.includes("Grant discount")){
                $( "#GrantDiscount" ).show();
            }
            if(taskName.includes("Deny discount")){
                $( "#DenyDiscount" ).show();
            }
            // and to properties not declared in the descriptor with
            taskAttrib = elem.businessObject.$attrs;
            attribArray.pop(taskAttrib);
        }
    });

/*// Print out Tasks
    for(var i=0; i< taskArray.length; i++){
        taskName += taskArray[i]+" ";
    }
     document.getElementById("taskNameField").innerHTML = taskName;*/

}