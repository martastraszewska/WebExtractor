
var $firstErrorAlert = false;
var $firstEmptyContentAlert = false;
var $firstContentAlert = false;
var $errorAlert = $("#errorAlert");
var $emptyContentAlert = $("#emptyContentAlert");
var $contentAlert = $("#contentAlert");

var $forrestAdv = $("#forrestAdv");

var $platformContent = $("#platformContent");
var $tableInit1 = "<h1 class='text-center' style='font-size:25px;'>";
var $tableInit2 = "<button class='btn btn-success btn-block' type='button'";
var $tableInit3 = ">Aktualizuj</button> "+
                                           "</h1> "+
                                            "<div class='table-responsive' "+
                                                 "style='background-repeat:no-repeat;background-size:contain;background-position:center;'> "+
                                                "<table class='table table-striped table-bordered table-hover' id='myTable'> "+
                                                    "<thead> "+
                                                     "<tr> ";

var $tableInit4 = "<th>Data ostatniej aktualizacji</th> "+

                                                    "</tr> "+
                                                    "</thead> "+
                                                    "<tbody id='content'> "+
                                                    "</tbody> "+
                                                "</table> "+
                                            "</div>";
$forrestAdv.click(function (){
    getForrestAdv();
});

function getForrestAdv(){
                      $platformContent.empty();
                      $platformContent.append($tableInit1 + $tableInit2 +
                      " id='updateForrestAdv' " +
                      $tableInit3 +
                      " <th>Nazwa źródła</th><th>Adres</th> " + $tableInit4);

                      getContent("forrestAdv");

                      $("#updateForrestAdv").click(function(){
                          update("forrestAdv");
                      });
                  }






function refreshContent(platform){
    if (platform == "forrestAdv"){
        getForrestAdv()}};


function update(){
$.ajax({
type: 'GET',
url: '/update',
success: function(content){
    if(content.status == 500 && $firstErrorAlert == false){
     $errorAlert.append("<div role='alert' class='alert alert-danger show'><span class='bg-warning' style='font-size:19px;'><strong>Błąd podczas pobierania danych</strong></span></div>");
     $errorAlert.toggle(6000,false);
     $firstErrorAlert = true;
    }else if (content.status == 500 && $firstErrorAlert == true) {
    $errorAlert.toggle(true);
    $errorAlert.toggle(4000,false);
    }else if(content.status == 200 && content.NumberOfUpdated == 0 && $firstEmptyContentAlert == false){
    $emptyContentAlert.append("<div role='alert' class='alert alert-warning show'><span class='bg-warning' style='font-size:19px;'><strong>Nie znaleziono zmienionych ogłoszeń</strong></span></div>");
    $emptyContentAlert.toggle(6000,false);
    $firstEmptyContentAlert = true;
    }else if(content.status == 200 && content.NumberOfUpdated == 0 && $firstEmptyContentAlert == true){
    $emptyContentAlert.toggle(true);
    $emptyContentAlert.toggle(6000,false);
    }else if(content.status == 200 && content.NumberOfUpdated != 0 && $firstContentAlert == false){
    $contentAlert.append("<div role='alert' class='alert alert-success show'><span class='bg-warning' style='font-size:19px;'><strong>Znaleziono zmienione ogłoszenia</strong></span></div>");
    $contentAlert.toggle(6000,false);
    $firstContentAlert = true;
    refreshContent(platform);
    }else if(content.status == 200 && content.NumberOfUpdated != 0 && $firstContentAlert == true){
    $contentAlert.toggle(true);
    $contentAlert.toggle(5000,false);
    refreshContent(platform);
    }
},
    error: function () {
            $errorAlert.append("<div role='alert' class='alert alert-danger show'><span class='bg-warning' style='font-size:19px;'><strong>Pobieranie trwa dłużej niż zwykle... odśwież stronę za jakiś czas</strong></span></div>");
            $errorAlert.toggle(8000, false);
            $firstErrorAlert = true;
    },
    timeout: 29000
});
};

function getContent(){
var $content = $("#content");

$.ajax({
type: 'GET',
url: '/getAll',
success: function(content){
    $content.empty();
    $.each(content, function(i,content){
            $content.append("<tr><td>"+content.name+"</td>"+

                    "<td><a target='_blank' href="+content.websiteUrl+">Link</a></td>"+
                    "<td>"+timeConverter(content.timestamp)+"</td>"+
                    "</tr>");
     }
    )
                        $('#myTable').DataTable({
                          "destroy": true, //use for reinitialize datatable
                          "language": {
                        "url": 'assets/json/Polish.json'
                         },
                         "order": [],
                         "pageLength": 50
                       });
}
});
};

function timeConverter(UNIX_timestamp){
        var a = new Date(UNIX_timestamp);
        var months = ['Sty','Lut','Mar','Kwi','Maj','Cze','Lip','Sie','Wrz','Paź','Lis','Gru'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();
        var hour = "0" + a.getHours();
        var min = "0" + a.getMinutes();
        var sec = "0" + a.getSeconds();
        var time = date + ' ' + month + ' ' + year + ' ' + hour.substr(-2) + ':' + min.substr(-2) + ':' + sec.substr(-2) ;
        return time;
      }
      console.log(timeConverter(0));
