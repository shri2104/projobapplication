//const express = require('express');
//const bodyParser = require('body-parser');
//const { MongoClient } = require('mongodb');
//const cors = require('cors');
//const multer = require('multer');
//const path = require('path');
//const fs = require('fs');
//
//const app = express();q
//const port = 3000;
//
//// MongoDB Connection
//const uri = "mongodb+srv://shrikant:SHRIkant@cluster0.ki7nc.mongodb.net/Projob_app?retryWrites=true&w=majority&appName=Cluster0";
//
//let database;
//MongoClient.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true })
//.then(client => {
//    console.log("Connected to MongoDB!");
//    database = client.db("Projob_app");
//})
//.catch(error => console.error("MongoDB connection error:", error));
//
//app.use(cors());
//app.use(bodyParser.json());
//
//// Set up multer storage
//const storage = multer.diskStorage({
//    destination: function (req, file, cb) {
//        const uploadPath = 'uploads/';
//        if (!fs.existsSync(uploadPath)) {
//            fs.mkdirSync(uploadPath, { recursive: true });
//        }
//        cb(null, uploadPath);
//    },
//    filename: function (req, file, cb) {
//        cb(null, Date.now() + path.extname(file.originalname));
//    }
//});
//
//const upload = multer({ storage: storage });
//
//// API to upload resume
//app.post('/uploadResume', upload.single('resume'), async (req, res) => {
//    if (!req.file) {
//        return res.status(400).send({ success: false, message: 'No file uploaded' });
//    }
//    const collection = database.collection("resumes");
//    const resumeData = {
//        userId: req.body.userId,  // Assume userId is sent in the request
//        filePath: req.file.path,
//        originalName: req.file.originalname,
//        uploadedAt: new Date()
//    };
//    try {
//        const result = await collection.insertOne(resumeData);
//        res.status(200).send({ success: true, id: result.insertedId, filePath: req.file.path });
//    } catch (error) {
//        console.error("Resume Upload Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to download resume
//app.get('/downloadResume/:userId', async (req, res) => {
//    const collection = database.collection("resumes");
//    const userId = req.params.userId;
//
//    try {
//        const resume = await collection.findOne({ userId: userId });
//        if (resume) {
//            res.download(resume.filePath, resume.originalName);
//        } else {
//            res.status(404).send({ success: false, message: 'Resume not found' });
//        }
//    } catch (error) {
//        console.error("Download Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to check if a resume exists for a user
//app.get('/checkResumeExists/:userId', async (req, res) => {
//    const collection = database.collection("resumes");
//    const userId = req.params.userId;
//
//    try {
//        const resume = await collection.findOne({ userId: userId });
//        if (resume) {
//            res.status(200).send({ success: true, exists: true, filePath: resume.filePath });
//        } else {
//            res.status(404).send({ success: true, exists: false, message: 'No resume found' });
//        }
//    } catch (error) {
//        console.error("Error checking resume:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/updateResume', upload.single('resume'), async (req, res) => {
//    if (!req.file) {
//        return res.status(400).send({ success: false, message: 'No file uploaded' });
//    }
//
//    const collection = database.collection("resumes");
//    const userId = req.body.userId;
//
//    try {
//        // Find the existing resume entry
//        const existingResume = await collection.findOne({ userId: userId });
//
//        if (existingResume) {
//            // Delete the old resume file
//            if (fs.existsSync(existingResume.filePath)) {
//                fs.unlinkSync(existingResume.filePath);
//            }
//
//            // Update the existing resume entry
//            await collection.updateOne(
//                    { userId: userId },
//            {
//                $set: {
//                filePath: req.file.path,
//                originalName: req.file.originalname,
//                uploadedAt: new Date(),
//            },
//            }
//            );
//
//            res.status(200).send({ success: true, message: "Resume updated successfully", filePath: req.file.path });
//        } else {
//            // If no previous resume, insert a new one
//            const result = await collection.insertOne({
//                userId: userId,
//                filePath: req.file.path,
//                originalName: req.file.originalname,
//                uploadedAt: new Date(),
//            });
//
//            res.status(200).send({ success: true, message: "Resume uploaded successfully", id: result.insertedId, filePath: req.file.path });
//        }
//    } catch (error) {
//        console.error("Resume Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.post('/storeData', async (req, res) => {
//    const collection = database.collection("projobapp");
//    const data = req.body;
//
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.post('/Userid', async (req, res) => {
//    const collection = database.collection("Userid");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/GetUserid/:email', async (req, res) => {
//    const collection = database.collection("Userid");
//    const email = req.params.email; // Retrieve email from the URL parameter
//    try {
//        const user = await collection.findOne({ email: email }); // Find user by email
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/comapnyData', async (req,res) => {
//    const collection = database.collection("comapnyData");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getallcompanies', async (req, res) => {
//    const collection = database.collection("comapnyData"); // Collection name: companies
//
//    try {
//        const companies = await collection.find({}).toArray(); // Retrieve all companies
//        res.status(200).send({ success: true, companies: companies }); // Return company details as an object
//    } catch (error) {
//        console.error("Retrieve Companies Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.get('/getcomapnyData/:userId', async (req, res) => {
//    const collection = database.collection("comapnyData");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.findOne({ userId: userId }); // Find user by userId
//
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/Candidatepersonaldata', async (req,res) => {
//    const collection = database.collection("Candidatepersonaldata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getCandidatepersonaldata/:userId', async (req, res) => {
//    const collection = database.collection("Candidatepersonaldata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.findOne({ userId: userId }); // Find user by userId
//
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.put('/updateCandidatepersonaldata/:userId', async (req, res) => {
//    const collection = database.collection("Candidatepersonaldata");
//    const userId = req.params.userId;
//    const updatedData = req.body;
//
//    try {
//        const result = await collection.updateOne(
//            { userId: userId }, // Find the user by userId
//            { $set: updatedData } // Update the user's data
//        );
//
//        if (result.modifiedCount > 0) {
//            res.status(200).send({ success: true, message: "User data updated successfully" });
//        } else {
//            res.status(404).send({ success: false, message: "User not found or data is the same" });
//        }
//    } catch (error) {
//        console.error("Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.post('/Candidatecontactladata', async (req,res) => {
//    const collection = database.collection("Candidatecontactladata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.get('/getCandidatecontactladata/:userId', async (req, res) => {
//    const collection = database.collection("Candidatecontactladata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.findOne({ userId: userId }); // Find user by userId
//
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.put('/updateCandidatecontactladata/:userId', async (req, res) => {
//    const collection = database.collection("Candidatecontactladata");
//    const userId = req.params.userId;
//    const updateData = req.body;
//
//    try {
//        const result = await collection.updateOne(
//            { userId: userId }, // Find user by userId
//            { $set: updateData } // Update with new data
//        );
//
//        if (result.matchedCount === 0) {
//            return res.status(404).send({ success: false, message: "User not found" });
//        }
//
//        res.status(200).send({ success: true, message: "Data updated successfully" });
//    } catch (error) {
//        console.error("Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//
//app.post('/Candidateeducationladata', async (req,res) => {
//    const collection = database.collection("Candidateeducationladata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getCandidateeducationladata/:userId', async (req, res) => {
//    const collection = database.collection("Candidateeducationladata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.find({ userId }).toArray();; // Find user by userId
//
//        if (user.length>0) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.put('/updateCandidateEducation/:id', async (req, res) => {
//    const collection = database.collection("Candidateeducationladata");
//    const id = req.params.id;
//    const updateData = req.body;
//
//    try {
//        const result = await collection.updateOne(
//            { id: id }, // Find user by userId
//            { $set: updateData } // Update with new data
//        );
//
//        if (result.matchedCount === 0) {
//            return res.status(404).send({ success: false, message: "User not found" });
//        }
//
//        res.status(200).send({ success: true, message: "Data updated successfully" });
//    } catch (error) {
//        console.error("Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/Candidateexperienceladata', async (req,res) => {
//    const collection = database.collection("Candidateexperienceladata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getCandidateexperienceladata/:userId', async (req, res) => {
//    const collection = database.collection("Candidateexperienceladata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.find({ userId }).toArray();; // Find user by userId
//
//        if (user.length>0) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.put('/updateCandidateexperienceladata/:id', async (req, res) => {
//    const collection = database.collection("Candidateexperienceladata");
//    const id = req.params.id;
//    const updateData = req.body;
//
//    try {
//        const result = await collection.updateOne(
//            { id: id }, // Find user by userId
//            { $set: updateData } // Update with new data
//        );
//
//        if (result.matchedCount === 0) {
//            return res.status(404).send({ success: false, message: "User not found" });
//        }
//
//        res.status(200).send({ success: true, message: "Data updated successfully" });
//    } catch (error) {
//        console.error("Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to Retrieve Data by Email
//app.get('/getData/:email', async (req, res) => {
//    const collection = database.collection("projobapp");
//    const email = req.params.email; // Retrieve email from the URL parameter
//    try {
//        const user = await collection.findOne({ email: email }); // Find user by email
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to Retrieve All Data
//app.get('/getAllData', async (req, res) => {
//    const collection = database.collection("projobapp");
//
//    try {
//        const allData = await collection.find().toArray(); // Retrieve all documents from the collection
//        res.status(200).send(allData); // Return all the data
//    } catch (error) {
//        console.error("Retrieve All Data Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to Store a New Job Posting
//
//app.post('/addJobToFavorites', async (req, res) => {
//    const collection = database.collection("savedjobs"); // Collection name: savedjobs
//    const data = req.body;
//
//    try {
//        // Ensure jobIds is an array, if not, convert it
//        if (typeof data.jobIds === 'string') {
//            data.jobIds = [data.jobIds]; // Convert a single jobId to an array
//        }
//
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getSavedJobIDs/:email', async (req, res) => {
//    const email = req.params.email; // Extract email from request params
//    const collection = database.collection("savedjobs");
//
//    try {
//        // Find all documents matching the email
//        const savedJobs = await collection.find({ email }).toArray();
//
//        // Extract unique jobIds
//        const jobIds = [...new Set(savedJobs.map(job => job.jobIds))];
//
//        if (jobIds.length > 0) {
//            res.status(200).json({ jobIds });
//        } else {
//            res.status(404).json({ message: 'No saved jobs found for this email' });
//        }
//    } catch (error) {
//        console.error("Fetch Error:", error);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//
//app.post('/addJobToFavorites', async (req, res) => {
//    const collection = database.collection("savedjobs"); // Collection name: savedjobs
//    const { email, jobId } = req.body; // Extract email and jobId from the request body
//    try {
//        const result = await collection.updateOne(
//            { email }, // Match the document by email
//            { $addToSet: { jobIds: jobId } }, // Add jobId to jobIds array if it doesn't already exist
//            { upsert: true } // Create the document if it doesn't exist
//        );
//        if (result.modifiedCount > 0 || result.upsertedCount > 0) {
//            res.status(200).json({ success: true, message: 'Job added to favorites' });
//        } else {
//            res.status(200).json({ success: true, message: 'Job already in favorites' });
//        }
//    }
//    catch (error) {
//        console.error("Add Error:", error);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//app.post('/deleteFavorite', async (req, res) => {
//    const collection = database.collection("savedjobs"); // Collection name: savedjobs
//    const { email, jobId } = req.body; // Extract email and jobId from the request body
//
//    try {
//        // Delete the document with the specified email and jobId
//        const result = await collection.deleteOne({ email, jobIds: { $in: [jobId] } });
//
//        if (result.deletedCount > 0) {
//            res.status(200).json({ success: true, message: 'Document deleted successfully' });
//        } else {
//            res.status(404).json({ success: false, message: 'Document not found' });
//        }
//    } catch (error) {
//        console.error("Delete Error:", error);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//// API to Check if a Job is Already in Favorites
//app.get('/isJobInFavorites/:email/:jobId', async (req, res) => {
//    const email = req.params.email; // Extract email from request params
//    const jobId = req.params.jobId; // Extract jobId from request params
//    const collection = database.collection("savedjobs"); // Collection name: savedjobs
//
//    try {
//        const savedJob = await collection.findOne({ email, jobIds: jobId }); // Check if jobId exists in jobIds array
//
//        if (savedJob) {
//            res.status(200).json({ success: true, message: 'Job is already in favorites' });
//        } else {
//            res.status(404).json({ success: false, message: 'Job is not in favorites' });
//        }
//    } catch (error) {
//        console.error("Check Error:", error);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//
//// Employers
//app.post('/JobPost', async (req, res) => {
//    const collection = database.collection("jobPosting");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getJobs', async (req, res) => {
//    const collection = database.collection("jobPosting"); // Collection name: jobPostings
//    const { contractType } = req.query; // Get contractType from query params
//
//    try {
//        let filter = {};
//        if (contractType) {
//            filter = { contractType: contractType }; // Filter based on contractType
//        }
//        const jobs = await collection.find(filter).toArray(); // Retrieve filtered job postings
//        res.status(200).send(jobs); // Return job postings
//    } catch (error) {
//        console.error("Retrieve Jobs Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/storeJob', async (req, res) => {
//    const collection = database.collection("jobPosting"); // Collection name: jobtracker
//    const data = req.body;
//
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/jobapplications', async (req, res) => {
//    const collection = database.collection("jobapplications");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//const { ObjectId } = require('mongodb'); // Import ObjectId from mongodb
//
//app.get('/getJobById/:jobid', async (req, res) => {
//    const collection = database.collection("jobPosting"); // Assuming your collection is named "jobs"
//    const jobid = req.params.jobid; // Retrieve jobid from the URL parameter
//
//    try {
//        const job = await collection.findOne({ jobid: jobid }); // Find job by jobid
//
//        if (job) {
//            res.status(200).send(job); // Return the job data
//        } else {
//            res.status(404).send({ success: false, message: 'Job not found' }); // If job doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getJobByemployerId/:Employerid', async (req, res) => {
//    const Employerid = req.params.Employerid;
//    const collection = database.collection("jobPosting");
//
//    try {
//        // Find all documents matching the Employerid
//        const jobs = await collection.find({ Employerid }).toArray();
//
//        if (jobs.length > 0) {
//            res.status(200).json(jobs);  // Directly return the array of jobs
//        } else {
//            res.status(404).json([]);  // Return an empty list if no jobs found
//        }
//    } catch (error) {
//        console.error("Fetch Error:", error.message);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//
//app.get('/getapplieduserids/:jobid', async (req, res) => {
//    const jobid = req.params.jobid;
//    const collection = database.collection("jobapplications");
//    try {
//        // Find all documents matching the job ID
//        const jobs = await collection.find({ jobid }).toArray();
//
//        if (jobs.length > 0) {
//            res.status(200).json(jobs);
//        } else {
//            res.status(404).json([]);
//        }
//    } catch (error) {
//        console.error("Fetch Error:", error.message);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//
//app.get('/getappliedjobids/:userid', async (req, res) => {
//    const userid = req.params.userid;
//    const collection = database.collection("jobapplications");
//    try {
//        // Find all documents matching the job ID
//        const jobs = await collection.find({ userid }).toArray();
//
//        if (jobs.length > 0) {
//            res.status(200).json(jobs);
//        } else {
//            res.status(404).json([]);
//        }
//    } catch (error) {
//        console.error("Fetch Error:", error.message);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//
//
//
//// API to upload company logo
//app.post('/uploadLogo', upload.single('logo'), async (req, res) => {
//    if (!req.file) {
//        return res.status(400).send({ success: false, message: 'No logo uploaded' });
//    }
//
//    const collection = database.collection("companyLogos");
//    const logoData = {
//        companyId: req.body.companyId,  // Pass company ID in the request
//        logoPath: req.file.path,
//        originalName: req.file.originalname,
//        uploadedAt: new Date()
//    };
//
//    try {
//        const result = await collection.insertOne(logoData);
//        res.status(200).send({ success: true, id: result.insertedId, logoPath: req.file.path });
//    } catch (error) {
//        console.error("Logo Upload Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to get company logo by company ID
//app.get('/getLogo/:companyId', async (req, res) => {
//    const companyId = req.params.companyId;
//    console.log("Requested companyId:", companyId);
//
//    const collection = database.collection("companyLogos");
//
//    try {
//        const logo = await collection.findOne({ companyId: companyId });
//
//        if (!logo) {
//            console.log("Logo not found for companyId:", companyId);
//            return res.status(404).send({ success: false, message: 'Logo not found' });
//        }
//
//        console.log("Serving logo from:", logo.logoPath);
//
//        const fs = require('fs');
//        if (!fs.existsSync(logo.logoPath)) {
//            console.log("Logo file does not exist:", logo.logoPath);
//            return res.status(404).send({ success: false, message: "Logo file not found" });
//        }
//
//        res.sendFile(path.join(__dirname, logo.logoPath));
//    } catch (error) {
//        console.error("Fetch Logo Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to add job preference
//app.post('/addJobPreference', async (req, res) => {
//    const collection = database.collection("jobPreferences");
//    const { userId, jobLocations, selectedSkills } = req.body;
//
//    try {
//        const result = await collection.insertOne({ userId, jobLocations, selectedSkills });
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Job Preference Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to get job preference by user ID
//app.get('/getJobPreference/:userId', async (req, res) => {
//    const collection = database.collection("jobPreferences");
//    const userId = req.params.userId;
//
//    try {
//        const preference = await collection.findOne({ userId: userId });
//        if (preference) {
//            res.status(200).send(preference);
//        } else {
//            res.status(404).send({ success: false, message: 'Job Preference not found' });
//        }
//    } catch (error) {
//        console.error("Retrieve Job Preference Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//// API to update job preference
//app.put('/updateJobPreference/:userId', async (req, res) => {
//    const collection = database.collection("jobPreferences");
//    const userId = req.params.userId;
//    const { jobLocations, selectedSkills } = req.body;
//
//    try {
//        const result = await collection.updateOne(
//            { userId: userId },
//            { $set: { jobLocations, selectedSkills } }
//        );
//        if (result.matchedCount === 0) {
//            res.status(404).send({ success: false, message: 'Job Preference not found' });
//        } else {
//            res.status(200).send({ success: true, message: 'Job Preference updated successfully' });
//        }
//    } catch (error) {
//        console.error("Job Preference Update Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//// API to delete job preference
//app.delete('/deleteJobPreference/:userId', async (req, res) => {
//    const collection = database.collection("jobPreferences");
//    const userId = req.params.userId;
//
//    try {
//        const result = await collection.deleteOne({ userId: userId });
//        if (result.deletedCount === 0) {
//            res.status(404).send({ success: false, message: 'Job Preference not found' });
//        } else {
//            res.status(200).send({ success: true, message: 'Job Preference deleted successfully' });
//        }
//    } catch (error) {
//        console.error("Job Preference Deletion Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/checkFollowStatus/:userId/:employerId', async (req, res) => {
//    const collection = database.collection("follows");
//    const { userId, employerId } = req.params;
//
//    if (!userId || !employerId) {
//        return res.status(400).send({ success: false, message: 'Missing userId or employerId' });
//    }
//
//    try {
//        // Check if the user follows the employer
//        const existingFollow = await collection.findOne({ userId, employerId });
//
//        if (existingFollow) {
//            res.status(200).send({ success: true, following: true });
//        } else {
//            res.status(200).send({ success: true, following: false });
//        }
//    } catch (error) {
//        console.error("Check Follow Status Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.post('/toggleFollow', async (req, res) => {
//    const collection = database.collection("follows");
//    const notificationsCollection = database.collection("notifications");
//    const { userId, employerId } = req.body;
//
//    if (!userId || !employerId) {
//        return res.status(400).send({ success: false, message: 'Missing userId or employerId' });
//    }
//
//    try {
//        // Check if user already follows the employer
//        const existingFollow = await collection.findOne({ userId, employerId });
//
//        if (existingFollow) {
//            // Unfollow (Remove from collection)
//            await collection.deleteOne({ userId, employerId });
//            res.status(200).send({ success: true, following: false, message: "Unfollowed employer successfully" });
//        } else {
//            // Follow (Insert into collection)
//            await collection.insertOne({ userId, employerId, followedAt: new Date() });
//
//            // Send Notification to Employer
//            await notificationsCollection.insertOne({
//                employerId,
//                userId,
//                message: `A new candidate has started following you.`,
//                createdAt: new Date(),
//                read: false
//            });
//
//            res.status(200).send({ success: true, following: true, message: "Followed employer successfully" });
//        }
//    } catch (error) {
//        console.error("Follow/Unfollow Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getNotifications/:employerId', async (req, res) => {
//    const { employerId } = req.params;
//
//    try {
//        const notifications = await database.collection("notifications")
//            .find({ employerId })
//            .toArray();
//        res.status(200).send({
//            success: true,
//            notifications: notifications.map(n => ({
//            id: n._id ? n._id.toString() : null,
//            employerId: n.employerId,
//            userId: n.userId,
//            message: n.message,
//            createdAt: n.createdAt,
//            read: n.read
//        }))
//        });
//    } catch (error) {
//        console.error("Error fetching notifications:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//
//
//app.delete('/deleteNotification/:notificationId', async (req, res) => {
//    const { notificationId } = req.params;
//
//    if (!notificationId) {
//        return res.status(400).send({ success: false, message: 'Missing notificationId' });
//    }
//
//    try {
//        const result = await database.collection("notifications").deleteOne({ _id: new ObjectId(notificationId) });
//
//        if (result.deletedCount === 1) {
//            res.status(200).send({ success: true, message: "Notification deleted successfully" });
//        } else {
//            res.status(404).send({ success: false, message: "Notification not found" });
//        }
//    } catch (error) {
//        console.error("Delete Notification Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.get('/getFollowedCompanies/:userId', async (req, res) => {
//    const collection = database.collection("follows");
//    const { userId } = req.params;
//
//    if (!userId) {
//        return res.status(400).send({ success: false, message: 'Missing userId' });
//    }
//
//    try {
//        // Fetch all employers that the user follows
//        const followedCompanies = await collection.find({ userId }).toArray();
//
//        res.status(200).send({
//            success: true,
//            companies: followedCompanies.map(follow => follow.employerId) // Returning only employerIds
//        });
//    } catch (error) {
//        console.error("Get Followed Companies Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.get('/getRecommendedJobs/:userId', async (req, res) => {
//    const { userId } = req.params;
//    try {
//        const preferencesCollection = database.collection("jobPreferences");
//        const jobsCollection = database.collection("jobPosting");
//
//        // Fetch user preferences
//        const userPreferences = await preferencesCollection.findOne({ userId });
//        if (!userPreferences) {
//            return res.status(404).send({ success: false, message: "User preferences not found" });
//        }
//
//        const { jobLocations, selectedSkills } = userPreferences;
//
//        // Fetch all jobs
//        const allJobs = await jobsCollection.find({}).toArray();
//
//        // Assign scores based on skill & location matches
//        const scoredJobs = allJobs.map(job => {
//            let skillMatchCount = job.keySkills.filter(skill => selectedSkills.includes(skill)).length;
//            let locationMatchCount = job.jobLocation.filter(location => jobLocations.includes(location)).length;
//
//            let score = (skillMatchCount * 2) + locationMatchCount;
//
//            return { job, score };
//        });
//
//        // Sort jobs by score
//        const sortedJobs = scoredJobs.sort((a, b) => b.score - a.score).map(item => item.job);
//
//        // Return only the jobs array
//        res.status(200).send(sortedJobs.slice(0, 10)); // Returning an array
//    } catch (error) {
//        console.error("Error fetching recommended jobs:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//app.get('/checkapplication/:jobid/:userid', async (req, res) => {
//    const { jobid, userid } = req.params;
//    const collection = database.collection("jobapplications");
//    try {
//        // Check if the combination of jobid and userid exists
//        const job = await collection.findOne({ jobid, userid });
//
//        if (job) {
//            res.status(200).json(true);  // Return true if found
//        } else {
//            res.status(404).json(false); // Return false if not found
//        }
//    } catch (error) {
//        console.error("Fetch Error:", error.message);
//        res.status(500).json({ success: false, error: error.message });
//    }
//});
//app.listen(port, () => {
//    console.log(`Server running at http://localhost:${port}`);
//});

