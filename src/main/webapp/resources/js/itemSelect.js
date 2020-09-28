/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//=================================For TAB1============================
 function addItemRow() {
    /* Declare variables */
    var elements, templateRow, rowCount, row, className, newRow, element, s, t;
    var BsIE = false;
    /* Get and count all "tr" elements with class="row".  The last one will be
     * serve as a template. */
    if (!document.getElementsByTagName)
      return false; /* DOM not supported */
    elements = document.getElementsByName("row_item");
    templateRow = null;
    rowCount = 0;
//	alert(elements.length);
    for (i = 0; i < elements.length; i++) {
      row = elements.item(i);

      /* Get the "class" attribute of the row. */
      className = null;
      if (row.getAttribute){
        className = row.getAttribute('class');

		}
      if (className == null && row.attributes) {
        /* getAttribute('class') always returns null on MSIE, and
         * row.attributes doesn't work on Firefox 1.0.  Go figure. */
        className = row.attributes['class'];
		BsIE=true;
      }

      /* This is not one of the rows we're looking for.  Move along. */
      if (!BsIE && (className != "row_item"))
        continue;
	  else if (BsIE && (className.value != "row_item"))
        continue;

      /* This *is* a row we're looking for. */
      templateRow = row;
      rowCount++;
    }
    if (templateRow == null)
      return false; /* Couldn't find a template row. */

    /* Make a copy of the template row */
    newRow = templateRow.cloneNode(true);

    /* Change the form variables e.g. price[x] -> price[rowCount] */
    elements = newRow.getElementsByTagName("input");
    for (i = 0; i < elements.length; i++) {
      element = elements.item(i);
      s = null;
      s = element.getAttribute("name");
      if (s == null)
        continue;
      t = s.split("[");
      if (t.length < 2)
        continue;
      s = t[0] + "[" + rowCount.toString() + "]";
      element.setAttribute("name", s);
      element.setAttribute("id", s);
	  element.setAttribute("value", "");
      element.value="";
    }
	elements = newRow.getElementsByTagName("select");
    for (i =0; i < elements.length; i++) {
      element = elements.item(i);
	  s = null;
      s = element.getAttribute("name");
	  //s = element.getAttribute("value");
	// alert (s);
      if (s == null)
        continue;
      t = s.split("[");
      if (t.length < 2)
        continue;
      s = t[0] + "[" + rowCount.toString() + "]";
     //element.setAttribute("name", "");
	  element.setAttribute("name", s);
      element.setAttribute("id", s);
	  element.setAttribute("value", "");
	//alert(s);
    }
document.addporder.hide.value=rowCount+1;
    /* Add the newly-created row to the table */
    templateRow.parentNode.appendChild(newRow);
    return true;
  }

function deleteItemRow(detail) {
                try {
                    var table = document.getElementById(detail);
                    var rowCount = table.rows.length;
                    var rowCount1=rowCount-2;
                    for(var i=0; i<rowCount; i++) {
                        var row = table.rows[i];
                        var chkbox = row.cells[0].childNodes[0];
                        if(null != chkbox && true == chkbox.checked) {
                            if(rowCount == 2) {
                                alert("Cannot delete all the rows.");
                                break;
                            }
                            table.deleteRow(i);
                            document.addporder.hide.value=rowCount1;
                            rowCount--;
                            i--;
                        }
                    }//document.write(rowCount);
                }catch(e) {
                    alert(e);
                }
            }


//===================================For======TAB2====================
function addItemRow2() {
  /* Declare variables */
  var elements, templateRow, rowCount, row, className, newRow, element, s, t;
  var BsIE = false;
  /* Get and count all "tr" elements with class="row".  The last one will be
   * serve as a template. */
  if (!document.getElementsByTagName)
    return false; /* DOM not supported */
  elements = document.getElementsByName("row_item2");
  templateRow = null;
  rowCount = 0;
//	alert(elements.length);
  for (i = 0; i < elements.length; i++) {
    row = elements.item(i);

    /* Get the "class" attribute of the row. */
    className = null;
    if (row.getAttribute){
      className = row.getAttribute('class');

		}
    if (className == null && row.attributes) {
      /* getAttribute('class') always returns null on MSIE, and
       * row.attributes doesn't work on Firefox 1.0.  Go figure. */
      className = row.attributes['class'];
		BsIE=true;
    }

    /* This is not one of the rows we're looking for.  Move along. */
    if (!BsIE && (className != "row_item2"))
      continue;
	  else if (BsIE && (className.value != "row_item2"))
      continue;

    /* This *is* a row we're looking for. */
    templateRow = row;
    rowCount++;
  }
  if (templateRow == null)
    return false; /* Couldn't find a template row. */

  /* Make a copy of the template row */
  newRow = templateRow.cloneNode(true);

  /* Change the form variables e.g. price[x] -> price[rowCount] */
  elements = newRow.getElementsByTagName("input");
  for (i = 0; i < elements.length; i++) {
    element = elements.item(i);
    s = null;
    s = element.getAttribute("name");
    if (s == null)
      continue;
    t = s.split("[");
    if (t.length < 2)
      continue;
    s = t[0] + "[" + rowCount.toString() + "]";
    element.setAttribute("name", s);
    element.setAttribute("id", s);
	  element.setAttribute("value", "");
    element.value="";
  }
	elements = newRow.getElementsByTagName("select");
  for (i =0; i < elements.length; i++) {
    element = elements.item(i);
	  s = null;
    s = element.getAttribute("name");
	  //s = element.getAttribute("value");
	// alert (s);
    if (s == null)
      continue;
    t = s.split("[");
    if (t.length < 2)
      continue;
    s = t[0] + "[" + rowCount.toString() + "]";
   //element.setAttribute("name", "");
	  element.setAttribute("name", s);
    element.setAttribute("id", s);
	  element.setAttribute("value", "");
	//alert(s);
  }
document.addporder.hide1.value=rowCount+1;
  /* Add the newly-created row to the table */
  templateRow.parentNode.appendChild(newRow);
  return true;
}

function deleteItemRow2(detail2) {
              try {
                  var table = document.getElementById(detail2);
                  var rowCount = table.rows.length;
                  var rowCount1=rowCount-2;
                  for(var i=0; i<rowCount; i++) {
                      var row = table.rows[i];
                      var chkbox = row.cells[0].childNodes[0];
                      if(null != chkbox && true == chkbox.checked) {
                          if(rowCount == 2) {
                              alert("Cannot delete all the rows.");
                              break;
                          }
                          table.deleteRow(i);
                          document.addporder.hide.value=rowCount1;
                          rowCount--;
                          i--;
                      }
                  }//document.write(rowCount);
              }catch(e) {
                  alert(e);
              }
          }






function addItemRowForEdit() {
    /* Declare variables */
    var elements, templateRow, rowCount, row, className, newRow, element, s, t;
    var BsIE = false;
    /* Get and count all "tr" elements with class="row".  The last one will be
     * serve as a template. */
    if (!document.getElementsByTagName)
      return false; /* DOM not supported */
    elements = document.getElementsByName("row_item");
    templateRow = null;
    rowCount = 0;
//	alert(elements.length);
    for (i = 0; i < elements.length; i++) {
      row = elements.item(i);

      /* Get the "class" attribute of the row. */
      className = null;
      if (row.getAttribute){
        className = row.getAttribute('class');

		}
      if (className == null && row.attributes) {
        /* getAttribute('class') always returns null on MSIE, and
         * row.attributes doesn't work on Firefox 1.0.  Go figure. */
        className = row.attributes['class'];
		BsIE=true;
      }

      /* This is not one of the rows we're looking for.  Move along. */
      if (!BsIE && (className != "row_item"))
        continue;
	  else if (BsIE && (className.value != "row_item"))
        continue;

      /* This *is* a row we're looking for. */
      templateRow = row;
      rowCount++;
    }
    if (templateRow == null)
      return false; /* Couldn't find a template row. */

    /* Make a copy of the template row */
    newRow = templateRow.cloneNode(true);

    /* Change the form variables e.g. price[x] -> price[rowCount] */
    elements = newRow.getElementsByTagName("input");
    for (i = 0; i < elements.length; i++) {
      element = elements.item(i);
      s = null;
      s = element.getAttribute("name");
      if (s == null)
        continue;
      t = s.split("[");
      if (t.length < 2)
        continue;
      s = t[0] + "[" + rowCount.toString() + "]";
      element.setAttribute("name", s);
      element.setAttribute("id", s);
	  element.setAttribute("value", "");
      element.value="";
    }
	elements = newRow.getElementsByTagName("select");
    for (i =0; i < elements.length; i++) {
      element = elements.item(i);
	  s = null;
      s = element.getAttribute("name");
	  //s = element.getAttribute("value");
	// alert (s);
      if (s == null)
        continue;
      t = s.split("[");
      if (t.length < 2)
        continue;
      s = t[0] + "[" + rowCount.toString() + "]";
     //element.setAttribute("name", "");
	  element.setAttribute("name", s);
	  element.setAttribute("value", "");
	//alert(s);

    }
document.editpOrder.hide.value=rowCount+1;
    /* Add the newly-created row to the table */
    templateRow.parentNode.appendChild(newRow);
    return true;
  }

function deleteItemRowForEdit(detail) {
                try {
                    var table = document.getElementById(detail);
                    var rowCount = table.rows.length;
                    var rowCount1=rowCount-2;
                    for(var i=0; i<rowCount; i++) {
                        var row = table.rows[i];
                        var chkbox = row.cells[0].childNodes[0];
                        if(null != chkbox && true == chkbox.checked) {
                            if(rowCount == 2) {
                                alert("Cannot delete all the rows.");
                                break;
                            }
                            table.deleteRow(i);
                            document.editpOrder.hide.value=rowCount1;
                            rowCount--;
                            i--;
                        }
                    }//document.write(rowCount);
                }catch(e) {
                    alert(e);
                }
            }
/**function intOnly(i) {
    var t = i.value;
    if(t.length>0) {
        t = t.replace(/[^\d\.]+/g, '');
    }
    var s = t.split('.');
    if(s.length>1) {
        s[1] = s[0] ;//+ '.' + s[1];
        s.shift(s);
    }
    i.value = s.join('');
}

function decimalOnly(i) {
    var t = i.value;
    if(t.length>0) {
        t = t.replace(/[^\d\.]+/g, '');
    }
    var s = t.split('.');
    if(s.length>1) {
        s[1] = s[0]+ '.' + s[1];
        s.shift(s);
    }
    i.value = s.join('');
}
*/