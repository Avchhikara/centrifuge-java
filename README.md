# centrifuge-java

### About

This centrifuge can basically though of as a main point of contact for the inner architecture. It will just hop from one target to other while intially using the input payload as the target payload and then consequently the output payload of each target to the next ones and the output of last one will be automatically passed-on back to the caller.

### Working

This Centrifuge will work as shown in the figure:

![Centifuge working illustration](https://res.cloudinary.com/mrmagician/image/upload/v1608046902/cf-j_y4usxa.png)

### Support

For now, the payload as text or an image is supported. Further more types' support can configured later.
