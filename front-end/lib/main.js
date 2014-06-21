var $ = require('jquery');

alert('asd');

$('.card-wrapper').click(function () {
	$(this).toggleClass('.card-wrapper-active');
});
