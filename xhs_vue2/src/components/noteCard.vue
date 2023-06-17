<template>
    <div>
        <el-card :body-style="{ padding: '0px',height:'100%'}" >
          <div @click="noteVisible=true">
            <img :src="require('../assets/'+card.url)" class="image"></div>
          <div style="padding: 14px;">
            <span>{{card.title}}</span>
              <el-row type="flex"  align="middle" class="bottom clearfix">
              <el-avatar :size="40" :src="require('../assets/'+card.sendurl)"></el-avatar>
              <span class="senduser">{{card.sendname}}</span>
              <el-button :icon="card.islike ? 'el-icon-solidheart' : 'el-icon-heart'" type = "text" size="mini" style="width:4rem" @click="changelike"></el-button>
              <span class="likespan">{{card.like}}</span>
              </el-row>
          </div>
        </el-card>
        <el-dialog
          :visible.sync="noteVisible"
          width="30%"
          :before-close="handleClose">
          <span>一熙的dialog</span>
          <span slot="footer" class="dialog-footer">
          </span>
        </el-dialog></div>
  </template>

  <script>
  export default{
    name:'noteCard',
    data()
    {
        return{
          noteVisible:false,
          card:{
          url:'head1.webp',
          title:'此处显示标题',
          sendurl:'head1.webp',//笔记发送者的头像
          sendname:'用户名',//笔记发送者的用户名
          like:15,//点赞数
          islike:false,
        }
        }
    },
    methods:{
      changelike(){
        this.card.islike=!this.card.islike;
        if(this.card.islike)
          this.card.like+=1;
        else
          this.card.like-=1;
      }
    }
    
}
  </script>
  