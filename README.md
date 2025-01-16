# web extractor
This is my first project that I've made more than 1,5 year ago. I just bumped up dependencies.

This is a small project that takes list of website urls from config 
file and extract part of html based on selector. Extracted data are compared to previous execution results. 
The idea of this program is to track any changes on given website. Application contains one 
front-end view that allows to display list of analyzed websites and contains a button that triggers
data refresh. Data refresh is running with fixed concurrency set up to 8 threads. 
