<@master template="layout/master">

<div class="main">
    <div class="container">
        <form id="uploadForm" name="uploadForm" class="mt10" method="post" action="" enctype="multipart/form-data">
            <span class="orangeButtonBox"><input id="uploadButton" name="uploadButton" type="button" value="Upload" onclick="uploadFile()"></span>
            <span class="loadingDiv displayNone" id="loadingLogo" style="display: none;width:40px;"></span>
        </form>
    </div>
</div>

<script type="text/javascript">
    function uploadFile() {
        $("#uploadOrderForm").ajaxSubmit({
            target: '#output',
            beforeSubmit: uploadFileBefore,
            success: uploadFileCallback,
            url: "<@url value='/orders/uploadOrders'/>",
            type: 'POST',
            dataType: 'json',
            clearForm: true,
            resetForm: true,
            timeout: 60000,
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.responseText == "The session is time out,please login again!") {
                    location.reload();
                } else {
                    $("#errorMsgDiv").children("p").html('We are sorry that upload progress has encountered an error, please try again!');
                    $("#errorMsgInfo").html('');
                    processResultBack('FAILED');
                }
            }
        });
    }

    function uploadFileBefore(formData, jqForm, options) {
        $("#uploadMessages").html('').hide();
        $("#errorMsgInfo").html('');
        var file = $("#file").val();
        if (null == file || "" == file) {
            showUploadMessages('Please select a file to upload.', 'red');
            return false;
        }
        if (null != file && (".xls" != file.substring(file.length - 4).toLowerCase() && ".xlsx" != file.substring(file.length - 5).toLowerCase())) {
            showUploadMessages("File must be uploaded in a xls or xlsx format.", 'red');
            $("#uploadOrderForm").clearForm();
            $("#uploadOrderForm").resetForm();
            return false;
        }
        var currentTime = format(new Date(), 'MM/dd/yyyy hh:mm');
        $("#currentTime").val(currentTime);
        $('#uploadButton').attr("disabled", true);
        $('#uploadButton').parent().addClass("cancelButtonBox").removeClass("orangeButtonBox");
        $('#uploadButton').attr("value", "Uploading...");
        $('#loadingLogo').css("display", "inline-block");
        return true;
    }

    function uploadFileCallback(responseText, statusText) {
        if (statusText = 'success') {
            if (responseText.msg == 'success') {
                processResultBack('SUCCESS');
                var uploadHistory = responseText.uploadHistory;
                appendUploadHistory(uploadHistory);
                return;
            }
            if (responseText.msg == 'failed') {
                $("#errorMsgDiv").children("p").html('Order response upload completed with errors. Please correct all errors for the following then upload again,');
                var errorMsgs = responseText.errorMsgs;
                for (var i = 0; i < errorMsgs.length; i++) {
                    $("<li style='list-style:disc;margin-top:5px;margin-left: 30px;'>" + errorMsgs[i] + "</li>").appendTo("#errorMsgInfo");
                }
                $("#errorMsgInfo").css('color', 'red');
                processResultBack('FAILED');
                return;
            }

            $("#errorMsgDiv").children("p").html(responseText.msg);
            $("#errorMsgInfo").html('');
            $("#errorMsgInfo").css('color', 'red');
            processResultBack('FAILED');

        }
    }
</script>
</@master>