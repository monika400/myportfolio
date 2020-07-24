// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */



 window.customElements.define('comment-element', class extends HTMLElement {'p'});
async function getComments(){
  const response = await fetch('/data');
  const comments = await response.json();
  container = document.getElementById('comments-container'); 
  comments.forEach((comment)=>{
      container.appendChild(createListElement(comment));
  });

  
}
function createListElement(text) {
  const element = document.createElement('comment-element');
  element.innerText = text.email +":" + text.commentText ;
  return element;
}

async function allowornot(){
    await getComments();
    const response = await fetch('/login');
    const userResponse = await response.json();
    const commentTextArea = document.getElementById('content1');
    const logoutuser = document.getElementById('userlogout');
    const loginUrl = document.getElementById('login-url');
    const loginuser = document.getElementById('userlogin');
    const logoutUrl = document.getElementById('logout-url');
    
    
    if (userResponse.LoggedIn) {
        loginuser.hidden = true;
        logoutUrl.href = userResponse.userURL;
        
        
     } 
     else{
        logoutuser.hidden = true;
        commentTextArea.hidden = true;
        loginUrl.href = userResponse.userURL;

     }
}







