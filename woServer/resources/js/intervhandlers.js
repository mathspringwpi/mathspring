var timeout = 0;

// This is for an attempt event that asks to highlight the hint button
// its a shame this function has to know the image files that are defined in the CSS rather than fetching them from it.
function highlightHintButton() {
    $("#hint").css('background-image','url(img/hint1.png)').fadeTo(500,0, function() {
        $("#hint").css('background-image', 'url(img/hint4.png)').fadeTo(1000, 1, function() {
            setTimeout(function() {
                $("#hint").css('background-image', 'url(img/hint4.png)').fadeTo(1000, 0, function() {
                    $("#hint").css('background-image','url(img/hint1.png)').fadeTo(300,1);
                });
            } ,2000);
        });
    });
}

// this pops up a dialog informing or asking about topic switching
function processTopicSwitchIntervention(html) {
    //alert("Switching topics because " + reason)
//    interventionDialogOpen("Switching Topics", html, NEXT_PROBLEM_INTERVENTION );
    interventionDialogOpenAsConfirm("Switching Topics", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );


}

function processAskEmotionIntervention(html) {
    //alert("Switching topics because " + reason);
//    interventionDialogOpen("How are you doing", html, NEXT_PROBLEM_INTERVENTION );
    interventionDialogOpenAsConfirm("How are you doing", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );

}

function processShowMPPIntervention () {
     showMPP();
}

function processMyProgressNavIntervention (html) {
//    interventionDialogOpen("Let's see our progress!", html, NEXT_PROBLEM_INTERVENTION);
    interventionDialogOpenAsConfirm("Let's see our progress!", html, NEXT_PROBLEM_INTERVENTION,myprogress );


}

function processMyProgressNavAskIntervention (html) {
//    interventionDialogOpen("Let's see our progress!", html, NEXT_PROBLEM_INTERVENTION);
    interventionDialogOpenAsYesNo("Let's see our progress!", html, NEXT_PROBLEM_INTERVENTION,myprogress,interventionDialogNoClick );
}

// This sets up an intervention on the helpers screen that freezes their input until the collaboration with
// a partner (on the partners computer) is done.   This checks every 3 seconds to see if this intervention
// is complete.   The server keeps returning the same intervention/learningCompanion when it's not complete and a different
// intervention when it is complete
function processCollaborationPartnerIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationPartnerIS";
    // Open the dialog with no buttons and a timeout function
    interventionDialogOpenNoButtons("Work with a partner", html, NEXT_PROBLEM_INTERVENTION,
        function(){
            // must provide destination because delegate intervention selectors no longer work
            servletGet("ContinueNextProblemIntervention", {probElapsedTime: globals.probElapsedTime, destination: globals.destinationInterventionSelector}, processNextProblemResult);
            globals.interventionType = null;
            globals.isInputIntervention= false;
        }, 3000);
}

function processCollaborationConfirmationIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationOriginatorIS";
//    interventionDialogOpen("Work with a partner", html, NEXT_PROBLEM_INTERVENTION);
    // TODO this could be changed to ...OpenAsYesNo funtion call instead of Confirm.  We'd have to change the html so it doesn't have inputs and
    // just says "Do you want to work with a partner?".  The yes/no buttons would then be all that needed to be clicked and functions would send back
    // those responses to the server
    interventionDialogOpenAsConfirm("Work with a partner", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );
}

// When the originator is waiting for a partner this checks the server every 5 seconds to see if the partner is available to work
// with.   Every 60 seconds it asks if they want to continue waiting for a partner.
function processCollaborationOriginatorIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationOriginatorIS";
    // Open the intervention dialog with no buttons and timeout handling
    interventionDialogOpenNoButtons("Waiting for a partner", html, NEXT_PROBLEM_INTERVENTION,
        // every minute it makes a request that results in an intervention that asks if they want to continue waiting for a partner
        // every 5 seconds it makes a request to see if the partner is available which results in the same intervention being put up if not
        // and a different intervention if the partner is available.
        function(){
                if(timeout >= 60000){
                    timeout = 0;
                    // must provide destination because delegate intervention selectors no longer work
                    servletGet("TimedIntervention", {probElapsedTime: globals.probElapsedTime, destination: globals.destinationInterventionSelector}, processNextProblemResult);
                }
                else{
                    timeout = timeout + 5000;
                    // must provide destination because delegate intervention selectors no longer work
                    servletGet("ContinueNextProblemIntervention", {probElapsedTime: globals.probElapsedTime, destination: globals.destinationInterventionSelector}, processNextProblemResult);
                }
                globals.interventionType = null;
                globals.isInputIntervention= false;}
            , 5000);
}




function processCollaborationFinishedIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationPartnerIS";
//    interventionDialogOpen("Collaboration over", html, NEXT_PROBLEM_INTERVENTION);
    interventionDialogOpenAsConfirm("Collaboration over", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );
}

function processCollaborationTimeoutIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationOriginatorIS";
//    interventionDialogOpen("Continue Waiting?", html, NEXT_PROBLEM_INTERVENTION );
    //  TODO This could be turned into a Yes/No dialog call
    interventionDialogOpenAsConfirm("Continue Waiting?", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );
}

function processCollaborationOptionIntervention(html) {
    globals.destinationInterventionSelector = "edu.umass.ckc.wo.tutor.intervSel2.CollaborationOriginatorIS";
//    interventionDialogOpen("Work with a partner?", html, NEXT_PROBLEM_INTERVENTION);
    interventionDialogOpenAsConfirm("Work with a partner?", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );
}


function processRapidAttemptIntervention (html) {
//    interventionDialogOpen("Answering Rapidly", html, ATTEMPT_INTERVENTION );
    interventionDialogOpenAsConfirm("Answering Rapidly", html, NEXT_PROBLEM_INTERVENTION,interventionDialogOKClick );

}


// Interventions that change the GUI come with some attributes that dictate the behavior:
// 1.   If notify is false,  just make changes to the GUI
// 2.  If notify is true, and when = 'before',  notify the user with a dialog that the GUI is being altered.  Then
//     when the user confirms by clicking on the dialog button, the GUI is actually changed.
// 3.  If notify is true and when='after',  change the GUI and then notify with a dialog.
//   Changing the GUI is specified by giving <component, action>.   The component is some on-screen item like a button
//   and the action is one of show/hide/highlight.
function processChangeGUIIntervention (activity) {
    var notify = activity.notify==true;
    var when = activity.when;
    var component = activity.component;
    var action = activity.action;
    var html = activity.html;
    // No notification means we just make the GUI change.
    if (!notify) {
        doGUIChange(component,action);
    }
    // The GUI change happens when the dialog is closed.  The callback function gets <component,action> from the transients object
    else if (when === 'before')  {
        transients.component = component;
        transients.componentAction = action;
        interventionDialogOpenAsConfirm("Making a change!", html, NEXT_PROBLEM_INTERVENTION, guiChanger );
    }
    // Notify after changing the GUI
    else if (when === 'after') {
        doGUIChange(component,action);
        // the noopHandler does nothing when the dialog is closed.
        interventionDialogOpenAsConfirm("Making a change!", html, NEXT_PROBLEM_INTERVENTION, noopHandler );
    }

}

// Intervention Dialog calls this method to perform the GUI change.   This relies on global variables that hold the component and action
function guiChanger () {
    doGUIChange(transients.component,transients.componentAction);
}

// Do nothing.
function noopHandler () {

}

// For now we just have a couple components that can be acted on but every item that has an id can be acted on in this way.
// TODO we'd need to make  show/hide/highlight into generic functions that can take a component id.
function doGUIChange(component, action) {
    if (component == 'MPP' && action == 'show')
        myprogress();
    else if (component == 'Hint' && action == 'highlight')
        highlightHintButton()


}
