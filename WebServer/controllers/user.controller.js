const User = require('../models/User.model');

const controller = {}

controller.editOwn = async (req, res, next) => {
    try {
        let {_id} = req.user;
        let {name, email} = req.body;
        const user = await User.findOne({_id: _id});
        if(!user){
            return res.status(404).json({error: "User not found"})
        }

        user.name = name;
        user.email = email;

        const newUser = await user.save();

        if (!newUser) {
            return res.status(500).json({error: "There was an error updating the profile"});
        }

        ({_id, name, email} = newUser);
        return res.status(200).json({_id, name, email});

    } catch (error) {
        next(error)
    }
}

module.exports = controller