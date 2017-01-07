'use strict';

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


/* Returned values are strange atm, start in the middle? of the bpmn and returns several tasks again and again */
function analyze() {
    var taskArray = [];
    var taskName;

    // Parsing Part
    var elementRegistry = viewer.get('elementRegistry');
    elementRegistry.forEach(function(elem, gfx) {
        if (elem.businessObject.$instanceOf('bpmn:Task')){
            // do something with the task
            var taskType = elem.businessObject.$type;

            // and access to properties declared in the descriptor with
            taskName = elem.businessObject.get('name');
            taskArray.push(taskName);

            // and to properties not declared in the descriptor with
            var taskAttrs = elem.businessObject.$attrs;
        }
    });


    for(var i=0; i< taskArray.length; i++){
        taskName += taskArray[0];
    }
     document.getElementById("taskNameField").innerHTML = taskName;
}