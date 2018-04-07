# ============================= IMPORTED LIBRARIES =============================

import json
import MySQLdb
import socket

from flask import Flask, session, redirect, url_for, escape, request



# ============================== GLOBAL VARIABLES ==============================

# Define application
app = Flask(__name__)


dbhost = "127.0.0.1"
#dbhost = "10.0.0.23"
dbuser = "root"
dbpasswd = ""
dbschema = "Test"


# ============================ ENDPOINT DEFINITIONS ============================
    
@app.route('/check', methods=['GET'])
def check():
    # Connect to the database
    db = MySQLdb.connect(host=dbhost,
        user=dbuser,
        passwd=dbpasswd,
        db=dbschema)

    # Create a cursor object, it lets us execute queries
    cur = db.cursor()

    # Use all the SQL you like
    query = "SELECT * FROM `products`"
    cur.execute(query)

    # Get results
    result = cur.fetchall()
    
    # Build list of dictionaries (assoc arrays)
    names = []
    for row in result:
        curr = {}
        curr['id'] = row[0]# doesnt exists. shhhh
        curr['title'] = row[1]
        curr['desc'] = row[2]
        curr['rating'] = row[3]
        curr['price'] = row[4]
        #curr['image'] = row[5]
        #curr['date'] = row[6] cant parse date
        curr['maker'] = row[7]
        names.append(curr)
    
    return json.dumps(names)
        


@app.errorhandler(500)
def logError(error):
    print error


# ============================== RUN APPLICATION ==============================
ip = socket.gethostbyname(socket.gethostname())

if __name__ == '__main__':
    app.run(debug=False, threaded=True, host=ip)
