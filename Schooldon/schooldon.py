from flask import Flask,render_template,redirect,request
import pymysql as py
app=Flask(__name__)
@app.route('/')
def display():
    try:
        db=py.Connect(host='localhost',user='root',password='',database='don')
        cur=db.cursor()
        sqq='select * from student_list'
        cur.execute(sqq)
        data=cur.fetchall()
    except Exception as e:
        print('Error:',e)    
    return render_template('dashbord.html',data=data)

@app.route('/create')
def create():
    return render_template('forms.html')

@app.route('/contact')
def contact():
    return render_template('contact.html')


@app.route('/edit/<rid>')
def edit(rid):
    try:
        db=py.Connect(host='localhost',user='root',password='',database='don')
        cur=db.cursor()
        sq3="select * from student_list where admno={}".format(rid)
        cur.execute(sq3)
        data=cur.fetchall()
        return render_template('editform.html',data=data)
    except Exception as e:
        print('Error',e)
    
        
@app.route('/store',methods=['POST'])
def store():
    adm=request.form['Admission number']
    name=request.form['Name']
    cls=request.form['Class']

    try:
        db=py.Connect(host='localhost',user='root',password='',database='don')
        cur=db.cursor()
        qu='insert into student_list(admno,Name,Class) values({},"{}",{})'.format(adm,name,cls)
        cur.execute(qu)
        db.commit()
    except Exception as e:
        print('FAILED to INSERT',e)
    return redirect('/')

@app.route('/update/<rid>',methods=['POST'])
def update(rid):
    adm=request.form['Admission number']
    name=request.form['Name']
    cls=request.form['Class']

    try:
        db=py.Connect(host='localhost',user='root',password='',database='don')
        cur=db.cursor()
        sq2="update student_list set admno='{}',Name='{}',Class='{}' WHERE admno={}".format(adm,name,cls,rid)
        cur.execute(sq2)
        db.commit()
    except Exception as e:
        print('Failed to update',e)
    return redirect('/')

    
@app.route('/delete/<rid>')
def delete(rid):
    try:
        db=py.Connect(host='localhost',user='root',password='',database='don')
        cur=db.cursor()
        sq1="delete from student_list where admno={}".format(rid)
        cur.execute(sq1)
        data=cur.fetchall()
        db.commit()
        return redirect('/')
    except Exception as e:
        print('Error',e)
    
app.run(debug=True)
