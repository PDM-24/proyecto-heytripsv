const Post = require("../models/Post.model")
const User = require("../models/User.model")
const axios = require("axios");
const cloudinary = require("cloudinary");

const controller = {}

controller.findUpcoming = async (req, res, next) => {
    try {
        const { pagination, limit, offset } = req.query;
        const posts = await Post.find({ date: { $gt: Date.now() } }, undefined, {
            sort: [{ date: 1 }],
            limit: pagination ? limit : undefined,
            skip: pagination ? offset : undefined
        }).populate("agency", "name number");

        return res.status(200).json({ posts, count: pagination ? await Post.countDocuments({ date: { $gt: Date.now() } }) : undefined });

    } catch (error) {
        next(error)
    }
}

controller.findRecent = async (req, res, next) => {
    try {
        const { pagination, limit, offset } = req.query;
        const posts = await Post.find({ date: { $gt: Date.now() } }, undefined, {
            sort: [{ createdAt: -1 }],
            limit: pagination ? limit : undefined,
            skip: pagination ? offset : undefined
        }).populate("agency", "name number");

        return res.status(200).json({ posts, count: pagination ? await Post.countDocuments({ date: { $gt: Date.now() } }) : undefined });

    } catch (error) {
        next(error)
    }
}

//Obtener las publicaciones reportadas
controller.findReported = async (req, res, next) => {
    try {

        const posts = await Post.find({ reports: { $exists: true, $not: { $size: 0 } } }).sort({ createdAt: -1 }).populate("agency", "name email").populate("reports.user", "name");

        return res.status(200).json(posts);

    } catch (error) {
        next(error);
    }
}

//Reportar post
controller.reportPost = async (req, res, next) => {
    try {
        const { id } = req.params;
        const { content } = req.body;
        const { _id } = req.user;

        let post = await Post.findOne({ _id: id });
        let user = await User.findOne({_id: _id})

        if (!post) {
            return res.status(404).json({ error: "Post not found" })
        }
        if (!user) {
            return res.status(403).json({error: "Forbidden"})
        }

        let _reports = post.reports;

        const prevIndex = _reports.findIndex(r => r.user.equals(_id));

        if (prevIndex >= 0) {
            //El comentario ya existe
            const _report = _reports[prevIndex];
            _report.content = content;
            _reports[prevIndex] = _report;
        } else {
            //El comentario no existe
            _reports = [..._reports, { user: _id, content: content }]

        }
        //Guardamos el post - commit
        post["reports"] = _reports;
        const newPost = (await post.save());

        //Retornamos el post actualizado
        return res.status(200).json({ newPost });

    } catch (error) {
        next(error);
    }
}

controller.findFromAgency = async(req, res, next) => {
    try {
        const {id} = req.params;
        const posts = await Post.find({agency: id}, undefined, {sort: [{createdAt: -1}]}).populate("agency", "name number");
        return res.status(200).json({ posts});
    } catch (error) {
        next(error)
    }
    

}

controller.savePost = async (req, res, next) => {
    try {
        const user = req.user;
        const { id } = req.params;
        let image = req.file ? true : false;

        const { title, description, date, meeting, itinerary,
            includes, category, lat, long, price } = req.body;

        let post = await Post.findById(id);

        if (!post) {
            post = new Post();

            post.agency = user._id;
        } else {
            if (!post["agency"].equals(user._id)) {
                return res.status(403).json({ error: "Forbidden" })
            }
        }

        post.title = title;
        post.description = description;
        post.date = date;
        post.meeting = meeting;
        post.itinerary = itinerary;
        post.includes = includes;
        post.category = category;
        post.lat = lat;
        post.long = long;
        post.price = price;


        const savedPost = await post.save({ setDefaultsOnInsert: true });
        if (image) {
            const timestamp = Math.round(new Date().getTime() / 1000);
            const signature = cloudinary.utils.api_sign_request({
                timestamp: timestamp,
                public_id: savedPost._id,
                upload_preset: "HeyTripSV",
                overwrite: true
            }, process.env.CLOUDINARY_SECRET);
            const b64 = Buffer.from(req.file.buffer).toString("base64");
            let dataURI = "data:" + req.file.mimetype + ";base64," + b64;
            const formData = new FormData();
            formData.append("file", dataURI);
            formData.append("upload_preset", "HeyTripSV");
            formData.append("cloud_name", "dlmtei8cc")
            formData.append("public_id", savedPost._id);
            formData.append("overwrite", true);
            formData.append("api_key", process.env.CLOUDINARY_API_KEY)
            formData.append("timestamp", timestamp);
            formData.append("signature", signature);

            const dataRes = await axios.post(
                process.env.CLOUDINARY_U,
                formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            }
            );
            imageUrl = dataRes.data.url;
            savedPost.image = imageUrl;
            await savedPost.save();
        }



        if (!savedPost) {
            return res.status(500).json({ error: "There was an error saving the post" });
        }

        return res.status(200).json({ savedPost });

    } catch (error) {
        next(error);
    }
}

controller.deleteOwn = async (req, res, next) => {
    try{
        const {id} = req.params;
        const {_id} = req.user;
        const response = await Post.deleteOne({$and: [{_id: id}, {agency: _id}]})

        if(response.deletedCount != 1){
            return res.status(500).json({error: "There was an error deleting the post"})
        }

        return res.status(200).json({response});


    }catch(error){
        next(error)
    }
}

controller.deletePost = async (req, res, next) => {
    try {
        
        const {id} = req.params;
        const response = await Post.deleteOne({_id: id})

        if(response.deletedCount != 1){
            return res.status(500).json({error: "There was an error deleting the post"})
        }

        return res.status(200).json({post});

    } catch (error) {
        next(error)
    }
}


module.exports = controller