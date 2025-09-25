**HOW TO RUN THE BACKEND**

1. When you download the project or pull changes from the repository you need to build the images:
docker compose build

2. Once you have built the images, you can run the backend with:
docker compose up -d

Once they are up, you'll be able to access the API, you can test it by calling GET http://localhost:8080/api/health and it should return a simple JSON showing { "status": "UP" }

3. To stop the backend:
docker compose down

**FILL THE DATABASE WITH DATA**
When you run the backend, the local database will be empty, to fill it with test data you can use the "fillDB.py" script following these steps:

1. Access the "scripts" folder that is located at the root of the project:
cd scripts

2. Create a virtual Python environment:
Windows: python -m venv venv
MacOS/Linux: python3 -m venv venv

3. Activate the virtual environment:
Windows: venv/Scripts/activate
MacOS/Linux: source venv/bin/activate

4. Install dependencies:
pip install -r requirements.txt

5. Run the script **(backend should be running)**:
Windows: python fillDB.py
MacOS/Linux: python3 fillDB.py

Once the script finished, the local database should be filled with test. You can test this by getting the users calling GET http://localhost:8080/api/users and checking if it returns any users.
