# ============================= IMPORTED LIBRARIES =============================

import json
import MySQLdb
import socket

from flask import Flask, session, redirect, url_for, escape, request



# ============================== GLOBAL VARIABLES ==============================

# Define application
app = Flask(__name__)


dbhost = "127.0.0.1"
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
    query = "SELECT * FROM `Products`"
    cur.execute(query)

    # Get results
    result = cur.fetchall()
    
    # Build list of dictionaries (assoc arrays)
    names = []
    for row in result:
        curr = {}
        curr['id'] = row[0]
        curr['title'] = row[1]
        curr['desc'] = row[2]
        curr['rating'] = row[3]
        curr['price'] = row[4]
        curr['image'] = row[5]
        names.append(curr)
    
    return json.dumps(names)

@app.route('/cat', methods=['GET'])
def cat():
    # Connect to the database
    db = MySQLdb.connect(host=dbhost,
        user=dbuser,
        passwd=dbpasswd,
        db=dbschema)

    # Create a cursor object, it lets us execute queries
    cur = db.cursor()

    # Use all the SQL you like
    query = "SELECT * FROM `categories`"
    cur.execute(query)

    # Get results
    result = cur.fetchall()
    
    # Build list of dictionaries (assoc arrays)
    names = []
    for row in result:
        curr = {}
        curr['id'] = row[0]
        curr['name'] = row[1]
        curr['role'] = row[2]
        names.append(curr)
    
    return json.dumps(names)

@app.route('/catSelect', methods=['GET'])
def catSelect():
    id = request.args['id']
    
    # Connect to the database
    db = MySQLdb.connect(host=dbhost,
        user=dbuser,
        passwd=dbpasswd,
        db=dbschema)

    # Create a cursor object, it lets us execute queries
    cur = db.cursor()

    # Use all the SQL you like
    query = "SELECT * FROM `products` WHERE category=%d" % int(id)
    cur.execute(query)

    # Get results
    result = cur.fetchall()
    
    # Build list of dictionaries (assoc arrays)
    names = []
    for row in result:
        curr = {}
        curr['id'] = row[0]
        curr['title'] = row[1]
        curr['desc'] = row[2]
        curr['rating'] = row[3]
        curr['price'] = row[4]
        names.append(curr)
        
    return json.dumps(names)



@app.errorhandler(500)
def logError(error):
    print error

ip = socket.gethostbyname(socket.gethostname())
# ============================== RUN APPLICATION ==============================

if __name__ == '__main__':
    app.run(debug=False, threaded=True, host=ip)

