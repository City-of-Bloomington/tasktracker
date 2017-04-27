clicked_button_id="";
var icons = {
    header:"ui-icon-circle-plus",
    activeHeader:"ui-icon-circle-minus"
};

$(".date").datepicker({
    nextText: "Next",
    prevText:"Prev",
    buttonText: "Pick Date",
    showOn: "both",
    navigationAsDateFormat: true,
    buttonImage: "/tasktracker/js/calendar.gif"
});

$("#selection_id").change(function() {
		$("#action2").val("refresh");
    $("#form_id").submit();
});

$("#a_disabled").attr('disabled','disabled');
$(document).on('click', 'a', function(e) {
    if ($(this).attr('disabled') == 'disabled') {
        e.preventDefault();
    }
});
$("#emp_name").autocomplete({
    source: APPLICATION_URL + "EmployeeService?format=json",
    minLength: 2,
		dataType:"json",
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#emp_name").val(ui.item.id);
						$("#phone").html(ui.item.phone);
						$("#dept").html(ui.item.dept);
						$("#h_phone").val(ui.item.phone);
						$("#h_email").val(ui.item.email);
						$("#h_dept").val(ui.item.dept);
						$("#h_division").val(ui.item.division);
						$("#h_title").val(ui.item.title);						
        }
    }
		})
//		.data('ui-autocomplete')._renderItem = function (ul, item) {
//        return $('<li>')
//						.attr("data-value",item.value)
//            .appendTo(ul);
//    };

jQuery(function ($) {
    var launcherClick = function(e)  {
            var openMenus   = $('.menuLinks.open'),
                menu        = $(e.target).siblings('.menuLinks');
            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);

            menu.removeClass('closed');
            menu.   addClass('open');
            e.stopPropagation();
        },
        documentClick = function(e) {
            var openMenus   = $('.menuLinks.open');

            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);
        };
    $('.menuLauncher').click(launcherClick);
    $(document       ).click(documentClick);
});
$(document).on("click","button", function (event) {
	clicked_button_id = event.target.id;
});

function doRefresh(){
		document.getElementById("action2").value="Refresh";		
		document.getElementById("form_id").submit();				
}
$('#show_info_button').click(function() {
    $('#show_info').hide();
    $('#hide_info').show();
		return false;
});
$('#hide_info_button').click(function() {
    $('#show_info').show();
    $('#hide_info').hide();
		return false;
});
function windowOpener(url, name, args) {
    if(typeof(popupWin) != "object" || popupWin.closed)  { 
        popupWin =  window.open(url, name, args); 
    } 
    else{ 
        popupWin.location.href = url; 
    }
		setTimeout(function(){popupWin.focus();},1000);
 }
function changeGroupUsers(obj, sct_id){
		var group_id = "";
		group_id = obj.options[obj.options.selectedIndex].value;
		if(group_id == '-1'){
				var sct = document.getElementById(sct_id);
				sct.options.length = 0;
				sct.options[0] = new Option ('Pick a User', ''); 
				sct.options[0].selected="true";
				return;
		}
		$.ajax({
				url: APPLICATION_URL + "GroupUserService?group_id="+group_id,
				dataType:'json'
		})
				.done(function( data, status ) {
						var sct = document.getElementById(sct_id);
						//
						// remove old options
						sct.options.length = 0;  // reset
						// start with empty option
						sct.options[0] = new Option ('Pick a User', ''); 
						sct.options[0].selected="true";
						for(var key in data){ // it is an array
								var obj2 = data[key];
								opt = document.createElement('option');
								opt.value=obj2.id; 
								opt.text=obj2.fullname; 
								sct.appendChild(opt);
						}
				})
				.error(function(x,status,err){
						alert(status+" "+err);
				});
}
function verifyCancel() {
		var x = confirm("Are you sure you want to cancel this request");
		if(x){
				document.getElementById("form_id").submit();
		}
		return x;
 }
