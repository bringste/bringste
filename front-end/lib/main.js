var $ = require('jquery');

$('.card-wrapper > .card-small > .table-view').click(function () {
  $(this).parent().parent().toggleClass('card-wrapper-active');
});

$('.checkbox').click(function () {
  $(this).toggleClass('checkbox-checked');
});
