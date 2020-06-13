function copyText(text) {
  navigator.clipboard.writeText(text);
}

function copyGithubCode() {
  /* Get the text field */
  var content = document.getElementById("useGithub").textContent;
  console.log(content);

  /* Copy the text inside the text field */
  copyText(content);

  /* Alert the copied text */
  alert("Copied to clipboard!");
}

function copyMavenCode() {
  /* Get the text field */
  var content = document.getElementById("useMaven").textContent;
  console.log(content);

  /* Copy the text inside the text field */
  copyText(content);

  /* Alert the copied text */
  alert("Copied to clipboard!");
}

function copyGradleCode() {
  /* Get the text field */
  var content = document.getElementById("useGradle").textContent;
  console.log(content);

  /* Copy the text inside the text field */
  copyText(content);

  /* Alert the copied text */
  alert("Copied to clipboard!");
}