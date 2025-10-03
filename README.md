**CONFIGURE THE ENV FILE**
Before running the project, you need to create a ".env" file at the root of the project with the following variables for the databse:

DB_NAME=<INSERT_DB_NAME>
DB_USER=<INSERT_DB_USER>
DB_PASSWORD=<INSERT_DB_PASSWORD>

You can freely choose the values for these variables, as when you build the images for the first time (which you'll do in the next section) the database container will create a new database within a volume with the specified characteristics.

If you need to change these values later, you'll need to delete the attached volume to recreate it, but that will delete all the data you currently have in the database.

To delete the volume run these commands:
1. List all volumes:
docker volume ls

2. Delete the database volume:
docker volume rm <VOLUME_NAME>

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
