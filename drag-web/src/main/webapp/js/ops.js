/**
 * Created by sky on 2017/7/27.
 */
$(function () {
    $(".timeLink").on('click',function (event) {
        event.stopPropagation();
        timeSortLink()
    });
    $(".moreError").on('click',function (event) {
        event.stopPropagation();
        dataLink()
    });

})

function dataLink() {

    $('#errorSortLink').modal('show')

}
function timeSortLink() {
    $('#timeSortLink').modal('show')
}
