// https://stackoverflow.com/questions/36975619/how-to-call-a-rest-web-service-api-from-javascript
geturl="";
posturl="";
var reqObj={};
const userGetAction = async (eleId) => {   // for GET
  const response = await fetch(geturl);//'http://localhost:8080/springsecurityimpl/api/downloadFileWithInfo/xyz@abc');
  const myJson = await response.json();
  console.log("wait to print");
  console.log(myJson);
  console.log(myJson.file);
  document.getElementById(eleId).src = "data:image/png;base64," + myJson.file;
}
const downloaFormUploadeddFile = function(){  // download file with some more info in json format
	geturl = 'http://localhost:8080/springsecurityimpl/api/downloadFileWithInfo/';
	email = document.getElementById('usrFormEmail').value;
	document.getElementById('usrFormEmail').value = "";
	geturl += email;
	userGetAction("downloadFormImg");
}
const downloaJsonUploadeddFile = function(){  // download file with some more info in json format
	geturl = 'http://localhost:8080/springsecurityimpl/api/downloadjsonfilewithinfo/';
	email = document.getElementById('usrJsonEmail').value;
	document.getElementById('usrJsonEmail').value = "";
	geturl += email;
	userGetAction("downloadJsonImg");
}
const userPostAction = async () => {     // for POST
	const response = await fetch(posturl, {//'http://localhost:8080/springsecurityimpl/api/uploadjsonfile', {
		method: 'POST',
	    body: reqObj, 
	    headers: {
	      'Content-Type': 'application/json'
	    }
	  });
	
	  const myJson = await response.data; //extract JSON from the http response
	  console.log(myJson);
}
const jsonUpload = function(){       // upload image with some associated info
	var imgName = document.getElementById('imgName').value;
	document.getElementById('imgName').value = "";
	var usrEmail = document.getElementById('usrEmail').value;
	document.getElementById('usrEmail').value = "";
	var files = document.getElementById('file').files;
	var fileResult;
	let reader = new FileReader();
    //reader.readAsText(files);
    reader.onload = function() {
      reqObj.name = imgName;
      reqObj.email = usrEmail;
      fileResult = reader.result;
      filter = fileResult.split(',');
      reqObj.file = filter[1];
      console.log(reqObj);
      reqObj = JSON.stringify(reqObj);
      console.log(reqObj);
      posturl = 'http://localhost:8080/springsecurityimpl/api/uploadjsonfile';
      userPostAction();
    };
    reader.onerror = function() {
      console.log(reader.error);
    };
   // reader.readAsText(files.item(0));  //readAsDataURL
   reader.readAsDataURL(files.item(0));
   //reader.readAsArrayBuffer(files.item(0));
}


