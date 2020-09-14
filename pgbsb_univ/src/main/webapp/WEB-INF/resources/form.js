$(function() {
	$('input.date-picker')
			.datepicker(
					{
						duration : '',
						yearRange : "-100:+0",
						format : 'dd-M-yyyy',
						constrainInput : true,
						changeMonth : true,
						changeYear : true,
						autoclose: true
					});
});

function remDoB(id) {
	$("#" + id).val('');
}