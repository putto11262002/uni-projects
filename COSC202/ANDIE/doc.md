<h1>Read Me for Part 2 </h1>
<h2>View Actions </h2>
<h3>Image cropping </h3>
<em> Suzanna Palacios </em> I implented the image cropping method through the use of the mouse listener which allows the users to select a region, then select the crop option in order to crop down to the selected region.

<h2>Filter Actions </h2>
<em>Alex Hopgood</em> I fixed the edges of images being left with a black border after applying filters. 
This was done by creating a resized version of the image, then drawing the image at it's original size in the center, before applying the filter to the whole image then cutting it down to it's original size again. This effectively cut the black borders off. 

<h2>Emboss Filter</h2>
<em>Sean RUssell</em> An image filter which returns an image that looks as if it has been pressed against paper. Used with RGB value normalisation to function as an edge detector.
<h3> Bugs/fixes </h3>

<em>Alex Hopgood</em> I adjusted all of our filters so that the background of transparent images wouldn't turn black after filters were applied. 

<h2> Draw Actions </h2>
<em>Alex Hopgood</em> I implemented the line and circle drawing functions, as well as changing drawAction to use bufferedImage. This allows the drawing to be undone and redone.

<h2>File with negative results</h2>
<em>Put Suthisrisinlpa</em> I implemented a convolution function that apply offset to account for both positive and negative values.

I implemented an option for filters that could potentially produce negative values to account for or ignore negative values. 
<h2> Macros </h2>
<em>Put Suthisrisinlpa</em> I implemented the recording feature the allows user to record the operations
they apply to an image and save them to an ops file. The user can reapply those operations to any other images by importing 
the saved ops file.

This feature can be access from the menu. The ability to start and stop recording can also be access via the
toolbar. The toolbar will also indicate whether the recording is ongoing or not via a blinking red icon.
<h2> Other Changes </h2>

<em>Suzanna Palacios</em> I created a mouse listener which allowed for the crop and the draw functions to be done by saving x and y coordinates and the height/width of a required drawing/cropping.

<em>Alex Hopgood</em> I created a prompt to save the file on exit if it hadn't previously been saved.

<h2> Known Issues </h2>

<h1>Read Me for Part 1 </h1>
<h2> File Actions </h2>
<h3> File export </h3>

<em>Suzanna Palacios</em> I created the file export function, which allows for the finished version of the image (with all filters and edits applied) to be saved. By implementing a choosable file filter, I allowed for saving in a variety of image formats, and prevent the image name from creating a corrupt file.

<h3> Bugs/fixes </h3>

<em>Suzanna Palacios</em> I applied a choosable file filter to the open action so that it can only open specific image files that have been tested to work with the code.<br><em>Suzanna Palacios</em> I switched 'save as' to a save dialogue approach so that it would work on mac. I also edited the save as action so that a copy of the original image is saved with the ops file.<br><em>Suzanna Palacios</em> After hearing that files don't display properly on windows, I changed the file filters so that file directories would also be visible under the filters.<br><em>Suzanna Palacios </em> I've applied an error message when a user tries to save or save as or export with no file open informing the user that there is no image available to save. <br><em> Suzanna Palacios </em> When opening a new image with the previous image open, all filters from the previous image are automatically applied. I wrote a code to clear the previous editable image before opening a new one.

<h3> Other changes </h3>

<em> Suzanna Palacios</em> I gave each of the file choosers a dialogue title so that the user can be reminded of what they are doing.

<h2> Edit/View Actions </h2>
<h3>Bugs/fixes<h3>

<h2> Filter Actions </h2>

<h3> Sharpen filter </h3>
<em>Alex Hopgood</em> I implemented a sharpen filter.<br>

<h3> Bugs/fixes </h3>

<em>Alex Hopgood</em> I changed the convolution operation so that the effect wouldn't be applied to edges in order to remove a black box that appeared around the image when the filter was applied. 

<h3> Soft blur </h3>
<h5> Team Member: Blaise Turner-Parker </h5>
<h5> Accessed Via:</h5> 
Filter menu <br>
Keyboard shortcut 'command + b'
<h5> Testing: </h5>
<h5> Bug Fixes: </h5>
<em>Alex Hopgood</em>  The soft blur and sharpen filters were similar and had the same problem with image edges so I changed the convolution operation so that the effect wouldn't be applied to edges in order to remove a black box that appeared around the image when the filter was applied. 


<h3> Gaussian blur </h3> 
<h5> Team Member: Blaise Turner-Parker </h5>
<h5> Accessed Via:</h5> 
Filter menu <br>
Keyboard shortcut 'command + g'
<h5> Testing: </h5>
<h5> Bug Fixes: </h5>
Error message for radius below 3 implemented in lieu of finding a solution. Implemented by Sean Russell. <br>
A black box was showing up around the image once filter was applied. Fixed by Sean Russell.<br>
<h5> Known Issues:</h5> 
Doesn't work for radius lower than 3. <br>

<h3> Median filter </h3>

<em>Put Suthisrisinlpa</em>


<strong>Bugs/Fixes</strong>
- Median filter, like other filters, had the problem with dark edges appearing as the radius of the kernel increases. To partially fix 
the issue, alter the logic that handles the pixels that have neighbours that are out of bounds, instead of it simple just remove this pixel
those pixels will be kept without applying any filtering. This will result in the edges of the image not being filtered. 


- The median filter is relative expedience operation when the kernel radius is large it could take up to 10 seconds when applying median filter to large images.
To optimize the prolonged runtime I eliminate redundancy operations. Instead of getting the extracting red, green, blue, alpha 
values everytime encounter a pixel ([Implmentation](https://altitude.otago.ac.nz/cosc202-bteam/andie/-/commit/7a760c22ce9b659744fe8cdde68899b534c8f42a)), 
before running the main loop, break the image into red, greed, blue, alpha and use these values in the main loop ([Implementation](https://altitude.otago.ac.nz/cosc202-bteam/andie/-/commit/dfed932ef10e1be44a1055fd4c18ccc37db5065f)).
[Stack Overflow reference](https://stackoverflow.com/a/1024795/18658456)

<strong>Testing</strong>

- Apply filter on various image sizes, orientation
- Apply filter with differen kernel size too ensure that as kernel size increases the smoothness of the image also increases.

<strong>Known issues</strong>
- Slow runtime with large radius values.
- Edges are not filtered.

<h3> Image resize </h3>
<h5> Team Member: Blaise Turner-Parker </h5>
<h5> Accessed Via:</h5> 
Filter menu <br>
Keyboard shortcut 'command + shift + r'
<h5> Testing: </h5>
Resized images of various aspect ratios to ensure their proportions would be maintained in the transformation.<br>
Upon resizing, had the function print the width and height of the new image to ensure that the transform was consistent with the expected size change. <br>
Reduced and increased images to determine the level of pixelation. Though this produced a less than optimal result, I still felt it was adequate for our purposes.

<h5> Known Issues:</h5> 
Serious pixelation when an image is reduced in size and then increased in size. <br>
Arbitrary limit of 10x size increase. <br>


<h2> Color Actions </h2>
<h3> Brightness and contrast adjustment </h3>
<em>Sean Russell</em> Filter to adjust the brightness and contrast of an image by a user-given percentage.

<h3>Posterisation effect</h3>
<em>Sean Russell</em> Filter used to give an image a "Poster" like effect.

<h3>Colour inversion filter<h3>
<em>Sean RUssell</em> Filter used to invert the colours of an image.
<h3> Bugs/fixes </h3>

<h2> Rotation Actions </h2>
<h3> Image rotation </h3>

<em>Put Suthisrisinlpa</em>

<strong>Overview</strong>
- Uses 2D graphics object to translate and rotate images pixels
- Allows image to rate left and right by multiple of 90 degrees

<strong>Testing</strong>
- Tested on images with various sizes and orientation including portrait and landscape.
- Tested on images that has been rotated

<strong>Bugs/Fixes</strong>
- To prevent large black boxes around the images when rotating I limit the rotation to multiples of 90 degrees



<h3> Image flip </h3>
<em>Sean Russell</em> Filter to flip an image horizontally or vertically.
<h3> Bugs/fixes </h3>

<h2> Other functions </h2>
<h3> Toolbar </h3>

<em>Put Suthisrisinlpa</em> 

<strong>Overview</strong>

- The toolbar is a subclass of java's JToolBar. 
- The actions that are available on the toolbar are: 
  - Save file
  - Undo 
  - Redo
  - Zoom in 
  - Zoom out
  - Rotate left
  - Rotate right
  - Horizontal flip
  - Vertical flip

- These actions have been added to the toolbar because they are among the most common operations.
None of the filters are added to the toolbar because they could add complex icons to the toolbar 
and these icons may not be apparent to some users.

<strong>Testing</strong>

- Tested that all the all toolbars icons triggers the correct icons.

<h3> Keyboard Shortcuts </h3>

<em>Put Suthisrisinlpa</em> 

<strong>Overview</strong>

- I implemented the keyboard shortcuts by mapping keys stroke to the actions' accelerator keys. 


- The keyboard shortcut mapping:
  - <code>Cmd</code>  + <code>shift</code> + <code>=</code> : Zoom in
  - <code>Cmd</code> + <code>-</code> : Zoom out 
  - <code>Cmd</code>  + <code>shift</code> + <code>h</code> : horizontal flip 
  - <code>Cmd</code> + <code>v</code> : vertical flip 
  - <code>Cmd</code> + <code>z</code> : Undo
  - <code>Cmd</code> + <code>y</code> : Redo 
  - <code>Cmd</code> + <code>r</code> : Rotate right
  - <code>Cmd</code> + <code>l</code> : Rotate left 
  - <code>Cmd</code> + <code>s</code> : Save 
  - <code>Cmd</code> + <code>a</code> : Save as 
  - <code>Cmd</code> + <code>o</code> : Open file 
  - <code>Cmd</code> + <code>m</code> : Mean filter 
  - <code>Cmd</code>  + <code>shift</code> +  <code>i</code> : Median filter 
  - <code>Cmd</code> + <code>g</code> : Gaussian blur filter 
  - <code>Cmd</code> + <code>b</code> : Soft Blur filter 
  - <code>Cmd</code> + <code>n</code> : Sharpen filter
  - <code>Cmd</code> + <code>shift</code> + <code>r</code> : Resize 
  - <code>Cmd</code> + <code>shift</code> +  <code>g</code> : Greyscale 
  - <code>Cmd</code> + <code>shift</code> +  <code>b</code> : Brightness 
  
  Not: On windows <code>Cmd</code> is <code>Ctrl</code>'

<strong>Testing</strong>

- The shortcuts mapping has been tested on both mac and windows. No formal testing framework is utilized, simple just
testing that each shortcut triggers the correct action.

<h3> General bugs/fixes </h3>
-<em> Suzanna Palacios </em>Gave error messages as appropriate when trying to perform actions with no image open, and when undoing/redoing with nothing in the stack.

<br>
<h2> Known Issues </h2>

- saving a file with the same name as another file would overwrite the previous file, this could be fixed if there was more time so that I could properly study the code required (<em>Suzanna Palacios</em>)

