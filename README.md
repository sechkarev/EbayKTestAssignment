# eBay Kleinanzeigen Android Coding Challenge

Congratulations! You made it to the eBay Kleinanzeigen remote coding challenge for Android. Here, we want to get a taste of your hands-on coding skills as well as your understanding of design and product descriptions.

## Background
Since our View Item Page (= VIP) is a bit outdated, we want to re-brush our VIP on all platforms – also on Android.


## To Do
Please create a new VIP for Android matching the design (see the design in project root folder for colors, font sizes, icons, 
etc - VIP_Design.pdf). All necessary image assets and colors are already defined in the res/ folder.

You can use any library or tool. It’s expected to apply an architectural pattern and to provide a well structured code.

### General information:
* See design of the sections in the attached design (sections are separated by a thick grey line)
* Size of sections adapts dynamically according the number of attributes (e.g. “Zimmer”) / contents (e.g. having additional documents such as PDF)
* If there is no content for a section (ex. no uploaded PDF, no attributes for “Ausstattung”, etc.), the section should collapse)

### Section: Images
* Show all the images in a horizontally swipeable gallery
* Show the first picture of the ad by default
* Show the share icon on the top right on the picture
* On the bottom left there is the number of the displayed picture and the number of pictures in total; divided by “/”
the user should be able to swipe through the gallery (Font size: 14, Text color: @color/black, Background color: @color/black_transparent)
* Clicking on an image opens it in a new screen with bigger resolution

### Section: Basic Info (directly below the picture) 
* Ad title (Json key: title)
* Price and currency (Json key: price)
* Address in the format "Street, ZIP Code, City" (it’s a link to google maps with the given latitude and longitude) (Json key: address)
* Calendar icon + creation date (Json key: posted-date-time)
* Views icon + number of visits (Json key: visits)
* Show ‘ID:’ and add Ad-ID (Json key: id)

### Section: Details
* Show headline: ‘Details’
* List all attributes coming from backend and the respective value and unit (Json key: attributes)

### Section: Features (Ausstattung) 
* Show headline ‘Ausstattung’
* List respective features in two columns (Json key: features)
* Keep order of features; logic: left, right, left, right, etc
* Put a green check before every feature in this section (asset provided in drawable folder)

### Section: Documents (PDFs)
* Show headline ‘Weitere Informationen’
* Show PDF icon and Name of PDF and chevron (Json key: documents)
* When the user clicks on a document, the respective PDF opens in a browser

### Section: Description
* Show headline ‘Beschreibung’
* Show the description coming from the backend (Json key: description)

## Technical Details
* You can use any library or tool
* It’s expected to apply an architectural pattern and to provide a well structured code
* Expected language for the challenge is Kotlin
* The app should request and parse content from our JSON service
* The app should load images from the URL that is part of the response

### API Details
* API endpoint URL: https://gateway.ebay-kleinanzeigen.de/mobile-api/candidate/ads/{ad_id}
* Ad ID to request: 1118635128
* Authorization: Basic Authentication
* Credentials:
  * Username: candidate
  * Password: yx6Xz62y

### Additional Info
* To generate the real image url replace the {imageId} placeholder with the string 1 or 57 accordingly for preview or full size.

Example:
`https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/$_{imageId}.JPG`
should be converted to
`https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/$_1.JPG` for preview
and
`https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/$_57.JPG` for full size image.

### Sample Json Output

**Run this curl command to get the sample data:**

```
curl --basic -u candidate:yx6Xz62y --user-agent "some-user-agent" https://gateway.ebay-kleinanzeigen.de/mobile-api/candidate/ads/1118635128
```

## Final Remarks

* You have 1 week to complete the challenge.
* It is expected to provide a solution with a good architectural structure and preferably with some tests.
* Please fill out the rest of this README file that explains your approach.
* Once you’re done with the challenge, please zip the project folder, upload it to Google Drive and send us the publicly available link to the zip file on Google Drive.

**Have fun!**


## Your comments / remarks

How did you approach the task?
What architecture-layers did you create and why?
What would you do if you had more time?
Which trade-offs did you take?

As soon as I received the task, I decided that it must be a good opportunity to put Jetpack Compose to use. I have very little experience with this library (on my current job we still create layouts in XML) and I'd work faster and be way more confident if I used the tools I am more familiar with, but the age of Compose is coming, and I think that the faster I get used to it, the better. And to be honest, I just wanted to try something new (or relatively new). 

I decided to not use Fragments — with Compose they are not a must anymore. First of all, I opened the [link](https://gateway.ebay-kleinanzeigen.de/mobile-api/candidate/ads/1118635128) in Chrome, copy-pasted the response and created the layout, hard-coding the values I have gotten from backend. I develop apps "top-to-bottom": first create the layouts and make them look as designed, and then connect real business logic to it.

After the UI was done (I have left a couple of visual bugs, but fixed them later), I wrote the ViewModel and the Use Case. I decided to use MVVM because it's a solution endorsed by Google and is basically the to-go approach in modern Android development — that's to be expected. The class "RetrieveApartmentInfo" and its method "invoke" might seem unusual though, but that's my favourite approach to structuring the Model layer: breaking business logic down to tiny easily testable chunks and inject them to ViewModels. With this approach, having a DI library is necessary — I added Koin because it's lightweight and I am familiar with it. As a finishing touch, I wrote unit tests.

What would I do if I had more time (also trade-offs): 
* Unit-test the UI. I have zero experience with Compose Testing kit, but heard that it makes writing UI tests less painful than it is now, and in the future I'll definitely give it a try. Especially if someone decouples Compose tests from real devices — as far as I know, right now these tests are still instrumented.
* Make the UI a bit more adaptive. For example, right now the color of the share icon depends on the theme, but ideally it should depend not on the theme but on the image it's drawn over. And it's hard to figure out, because I don't know how the image will look like before I draw it. There must be libraries that take care of this, but in a real case I will first and foremost go to our designers and discuss this topic with them. Same with the status bar: it is transparent when we draw a photo behind it, and that's cool, but we need to make it opaque again on scrolling (I saw how it is done in the real Kleinanzeigen app — nicely!), and I know how to do this... without Compose. Again, that's a topic worth discussing, and since there were no requirements, I decided to leave it as is because any beautiful solution would be too time-consuming :(
* Support the Dark Theme. But there were neither requirements nor designs, and I decided to not bother.
