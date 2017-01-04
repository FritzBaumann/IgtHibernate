/* Parse BPMN File for Tasks

var BpmnViewer = require('bpmn-js');

var xml; // my BPMN 2.0 xml
var viewer = new BpmnViewer({ container: 'body' });

viewer.importXML(xml, function(err) {

    if (err) {
        console.log('error rendering', err);
    } else {
        console.log('rendered');
        // <<====== ***** HERE *****
        var elementRegistry = viewer.get('elementRegistry');
        elementRegistry.forEach(function(elem, gfx) {
            if (elem.$instanceOf('bpmn:Task')){
                // do something with the task
            }
        });
    }
});*/
