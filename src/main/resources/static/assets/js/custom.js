$(document).ready(function () {
  $("#customerTable").DataTable({
    // scrollX: true,
    pagingType: "simple",
    // pageLength: 30,
    // fixedHeader: {
    //   header: true,
    // },
  });
  $("#courseTableAdmin").DataTable({
    // scrollX: true,
    pagingType: "simple",
     pageLength: 50,
    // fixedHeader: {
    //   header: true,
    // },
  });
  $("#courseTableUser").DataTable({
    // scrollX: true,
    pagingType: "simple",
    pageLength: 50,
    // fixedHeader: {
    //   header: true,
    // },
  });
});
