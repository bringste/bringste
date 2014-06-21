var $ = require('jquery');

$('.card-wrapper > .card-small > .table-view').click(function () {
  $(this).parent().parent().toggleClass('card-wrapper-active');
});

$('.checkbox').click(function () {
  $(this).toggleClass('checkbox-checked');
});

var cloneTextField = function() {
  console.log('a');
  if ($(this).val().length > 0 && !$(this).next().hasClass('itemTextField')) {
  console.log('b');
    var clone = $(this).clone();
    clone.val('');
    clone.insertAfter($(this));
    clone.on('change keyup paste', cloneTextField);
  } else if ($(this).val().length == 0 && $(this).next().hasClass('itemTextField')) {
    $(this).remove();
  }
}

$('.itemTextField').on('change keyup paste', cloneTextField);
