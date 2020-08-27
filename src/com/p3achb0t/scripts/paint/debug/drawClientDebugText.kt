package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.LoginResponse
import com.p3achb0t.api.wrappers.utils.Calculations
import java.awt.Color
import java.awt.Font
import java.awt.Graphics



fun drawClientDebugText(g: Graphics, ctx: Context) {

    val debugText = arrayListOf<DebugText>()
    if (true) {

        g.color = Color.white
        debugText.add(DebugText("get__t_n       : ${ctx.client.get__t_n()}"))
        debugText.add(DebugText("get__w_if      : ${ctx.client.get__w_if()}"))
        debugText.add(DebugText("get__g_rf      : ${ctx.client.get__g_rf()}"))
        debugText.add(DebugText("get__q_im      : ${ctx.client.get__q_im()}"))
        debugText.add(DebugText("get__v_be      : ${ctx.client.get__v_be()}"))
        debugText.add(DebugText("get__r_aj      : ${ctx.client.get__r_aj()}"))
        debugText.add(DebugText("get__ag_gl     : ${ctx.client.get__ag_gl()}"))
        debugText.add(DebugText("get__ae_g      : ${ctx.client.get__ae_g()}"))
        debugText.add(DebugText("get__ap_ew     : ${ctx.client.get__ap_ew()}"))
        debugText.add(DebugText("get__av_i      : ${ctx.client.get__av_i()}"))
        debugText.add(DebugText("get__aj_ey     : ${ctx.client.get__aj_ey()}"))
        debugText.add(DebugText("get__bl_gk     : ${ctx.client.get__bl_gk()}"))
        debugText.add(DebugText("get__bz_cf     : ${ctx.client.get__bz_cf()}"))
        debugText.add(DebugText("get__bz_ch     : ${ctx.client.get__bz_ch()}"))
        debugText.add(DebugText("get__bz_cj     : ${ctx.client.get__bz_cj()}"))
        debugText.add(DebugText("get__bz_ck     : ${ctx.client.get__bz_ck()}"))
        debugText.add(DebugText("get__bz_co     : ${ctx.client.get__bz_co()}"))
        debugText.add(DebugText("get__bz_cs     : ${ctx.client.get__bz_cs()}"))
        debugText.add(DebugText("get__bg_g      : ${ctx.client.get__bg_g()}"))
        debugText.add(DebugText("get__bp_ef     : ${ctx.client.get__bp_ef()}"))
        debugText.add(DebugText("get__bw_m      : ${ctx.client.get__bw_m()}"))
        debugText.add(DebugText("get__bw_n      : ${ctx.client.get__bw_n()}"))
        debugText.add(DebugText("get__bw_s      : ${ctx.client.get__bw_s()}"))
        debugText.add(DebugText("get__be_ij     : ${ctx.client.get__be_ij()}"))
        debugText.add(DebugText("get__bu_g      : ${ctx.client.get__bu_g()}"))
        debugText.add(DebugText("get__bu_e      : ${ctx.client.get__bu_e()}"))
        debugText.add(DebugText("get__bu_l      : ${ctx.client.get__bu_l()}"))
        debugText.add(DebugText("get__by_gy     : ${ctx.client.get__by_gy()}"))
        debugText.add(DebugText("get__by_sz     : ${ctx.client.get__by_sz()}"))
        debugText.add(DebugText("get__bo_rc     : ${ctx.client.get__bo_rc()}"))
        debugText.add(DebugText("get__client_sr : ${ctx.client.get__client_sr()}"))
        debugText.add(DebugText("get__client_cf : ${ctx.client.get__client_cf()}"))
        debugText.add(DebugText("get__client_cj : ${ctx.client.get__client_cj()}"))
        debugText.add(DebugText("get__client_da : ${ctx.client.get__client_da()}"))
        debugText.add(DebugText("get__client_de : ${ctx.client.get__client_de()}"))
        debugText.add(DebugText("get__client_eg : ${ctx.client.get__client_eg()}"))
        debugText.add(DebugText("get__client_em : ${ctx.client.get__client_em()}"))
        debugText.add(DebugText("get__client_eq : ${ctx.client.get__client_eq()}"))
        debugText.add(DebugText("get__client_ff : ${ctx.client.get__client_ff()}"))
        debugText.add(DebugText("get__client_fk : ${ctx.client.get__client_fk()}"))
        debugText.add(DebugText("get__client_gj : ${ctx.client.get__client_gj()}"))
        debugText.add(DebugText("get__client_gm : ${ctx.client.get__client_gm()}"))
        debugText.add(DebugText("get__client_gr : ${ctx.client.get__client_gr()}"))
        debugText.add(DebugText("get__client_gu : ${ctx.client.get__client_gu()}"))
        debugText.add(DebugText("get__client_gv : ${ctx.client.get__client_gv()}"))
        debugText.add(DebugText("get__client_gw : ${ctx.client.get__client_gw()}"))
        debugText.add(DebugText("get__client_hd : ${ctx.client.get__client_hd()}"))
        debugText.add(DebugText("get__client_hh : ${ctx.client.get__client_hh()}"))
        debugText.add(DebugText("get__client_hi : ${ctx.client.get__client_hi()}"))
        debugText.add(DebugText("get__client_hk : ${ctx.client.get__client_hk()}"))
        debugText.add(DebugText("get__client_hl : ${ctx.client.get__client_hl()}"))
        debugText.add(DebugText("get__client_hm : ${ctx.client.get__client_hm()}"))
        debugText.add(DebugText("get__client_ho : ${ctx.client.get__client_ho()}"))
        debugText.add(DebugText("get__client_hp : ${ctx.client.get__client_hp()}"))
        debugText.add(DebugText("get__client_hw : ${ctx.client.get__client_hw()}"))
        debugText.add(DebugText("get__client_hy : ${ctx.client.get__client_hy()}"))
        debugText.add(DebugText("get__client_ig : ${ctx.client.get__client_ig()}"))
        debugText.add(DebugText("get__client_ii : ${ctx.client.get__client_ii()}"))
        debugText.add(DebugText("get__client_io : ${ctx.client.get__client_io()}"))
        debugText.add(DebugText("get__client_ip : ${ctx.client.get__client_ip()}"))
        debugText.add(DebugText("get__client_iq : ${ctx.client.get__client_iq()}"))
        debugText.add(DebugText("get__client_is : ${ctx.client.get__client_is()}"))
        debugText.add(DebugText("get__client_iv : ${ctx.client.get__client_iv()}"))
        debugText.add(DebugText("get__client_iw : ${ctx.client.get__client_iw()}"))
        debugText.add(DebugText("get__client_ja : ${ctx.client.get__client_ja()}"))
        debugText.add(DebugText("get__client_jb : ${ctx.client.get__client_jb()}"))
        debugText.add(DebugText("get__client_je : ${ctx.client.get__client_je()}"))
        debugText.add(DebugText("get__client_jf : ${ctx.client.get__client_jf()}"))
        debugText.add(DebugText("get__client_jr : ${ctx.client.get__client_jr()}"))
        debugText.add(DebugText("get__client_kh : ${ctx.client.get__client_kh()}"))
        debugText.add(DebugText("get__client_kp : ${ctx.client.get__client_kp()}"))
        debugText.add(DebugText("get__client_kt : ${ctx.client.get__client_kt()}"))
        debugText.add(DebugText("get__client_lh : ${ctx.client.get__client_lh()}"))
        debugText.add(DebugText("get__client_li : ${ctx.client.get__client_li()}"))
        debugText.add(DebugText("get__client_lm : ${ctx.client.get__client_lm()}"))
        debugText.add(DebugText("get__client_ls : ${ctx.client.get__client_ls()}"))
        debugText.add(DebugText("get__client_ma : ${ctx.client.get__client_ma()}"))
        debugText.add(DebugText("get__client_mc : ${ctx.client.get__client_mc()}"))
        debugText.add(DebugText("get__client_mf : ${ctx.client.get__client_mf()}"))
        debugText.add(DebugText("get__client_ms : ${ctx.client.get__client_ms()}"))
        debugText.add(DebugText("get__client_mv : ${ctx.client.get__client_mv()}"))
        debugText.add(DebugText("get__client_mw : ${ctx.client.get__client_mw()}"))
        debugText.add(DebugText("get__client_nb : ${ctx.client.get__client_nb()}"))
        debugText.add(DebugText("get__client_nd : ${ctx.client.get__client_nd()}"))
        debugText.add(DebugText("get__client_ne : ${ctx.client.get__client_ne()}"))
        debugText.add(DebugText("get__client_ng : ${ctx.client.get__client_ng()}"))
        debugText.add(DebugText("get__client_ni : ${ctx.client.get__client_ni()}"))
        debugText.add(DebugText("get__client_nr : ${ctx.client.get__client_nr()}"))
        debugText.add(DebugText("get__client_nt : ${ctx.client.get__client_nt()}"))
        debugText.add(DebugText("get__client_nu : ${ctx.client.get__client_nu()}"))
        debugText.add(DebugText("get__client_nz : ${ctx.client.get__client_nz()}"))
        debugText.add(DebugText("get__client_od : ${ctx.client.get__client_od()}"))
        debugText.add(DebugText("get__client_oe : ${ctx.client.get__client_oe()}"))
        debugText.add(DebugText("get__client_oi : ${ctx.client.get__client_oi()}"))
        debugText.add(DebugText("get__client_oj : ${ctx.client.get__client_oj()}"))
        debugText.add(DebugText("get__client_on : ${ctx.client.get__client_on()}"))
        debugText.add(DebugText("get__client_oq : ${ctx.client.get__client_oq()}"))
        debugText.add(DebugText("get__client_ox : ${ctx.client.get__client_ox()}"))
        debugText.add(DebugText("get__client_pd : ${ctx.client.get__client_pd()}"))
        debugText.add(DebugText("get__client_ps : ${ctx.client.get__client_ps()}"))
        debugText.add(DebugText("get__client_pt : ${ctx.client.get__client_pt()}"))
        debugText.add(DebugText("get__client_py : ${ctx.client.get__client_py()}"))
        debugText.add(DebugText("get__client_qa : ${ctx.client.get__client_qa()}"))
        debugText.add(DebugText("get__client_qf : ${ctx.client.get__client_qf()}"))
        debugText.add(DebugText("get__client_qu : ${ctx.client.get__client_qu()}"))
        debugText.add(DebugText("get__client_qv : ${ctx.client.get__client_qv()}"))
        debugText.add(DebugText("get__client_si : ${ctx.client.get__client_si()}"))
        debugText.add(DebugText("get__client_ss : ${ctx.client.get__client_ss()}"))
        debugText.add(DebugText("get__client_su : ${ctx.client.get__client_su()}"))
        debugText.add(DebugText("get__ca_rb     : ${ctx.client.get__ca_rb()}"))
        debugText.add(DebugText("get__ct_x      : ${ctx.client.get__ct_x()}"))
        debugText.add(DebugText("get__cv_ac     : ${ctx.client.get__cv_ac()}"))
        debugText.add(DebugText("get__cv_et     : ${ctx.client.get__cv_et()}"))
        debugText.add(DebugText("get__ce_z      : ${ctx.client.get__ce_z()}"))
        debugText.add(DebugText("get__ci_j      : ${ctx.client.get__ci_j()}"))
        debugText.add(DebugText("get__cx_ae     : ${ctx.client.get__cx_ae()}"))
        debugText.add(DebugText("get__cx_ap     : ${ctx.client.get__cx_ap()}"))
        debugText.add(DebugText("get__cx_at     : ${ctx.client.get__cx_at()}"))
        debugText.add(DebugText("get__cx_bb     : ${ctx.client.get__cx_bb()}"))
        debugText.add(DebugText("get__cx_bd     : ${ctx.client.get__cx_bd()}"))
        debugText.add(DebugText("get__cx_bm     : ${ctx.client.get__cx_bm()}"))
        debugText.add(DebugText("get__cx_bz     : ${ctx.client.get__cx_bz()}"))
        debugText.add(DebugText("get__cx_j      : ${ctx.client.get__cx_j()}"))
        debugText.add(DebugText("get__cx_s      : ${ctx.client.get__cx_s()}"))
        debugText.add(DebugText("get__ck_h      : ${ctx.client.get__ck_h()}"))
        debugText.add(DebugText("get__ck_w      : ${ctx.client.get__ck_w()}"))
        debugText.add(DebugText("get__dv_aw     : ${ctx.client.get__dv_aw()}"))
        debugText.add(DebugText("get__dy_c      : ${ctx.client.get__dy_c()}"))
        debugText.add(DebugText("get__ds_rp     : ${ctx.client.get__ds_rp()}"))
        debugText.add(DebugText("get__du_g      : ${ctx.client.get__du_g()}"))
        debugText.add(DebugText("get__dt_ri     : ${ctx.client.get__dt_ri()}"))
        debugText.add(DebugText("get__ee_aq     : ${ctx.client.get__ee_aq()}"))
        debugText.add(DebugText("get__eu_re     : ${ctx.client.get__eu_re()}"))
        debugText.add(DebugText("get__ep_h      : ${ctx.client.get__ep_h()}"))
        debugText.add(DebugText("get__ep_i      : ${ctx.client.get__ep_i()}"))
        debugText.add(DebugText("get__ep_m      : ${ctx.client.get__ep_m()}"))
        debugText.add(DebugText("get__ep_x      : ${ctx.client.get__ep_x()}"))
        debugText.add(DebugText("get__ec_av     : ${ctx.client.get__ec_av()}"))
        debugText.add(DebugText("get__ec_q      : ${ctx.client.get__ec_q()}"))
        debugText.add(DebugText("get__eb_c      : ${ctx.client.get__eb_c()}"))
        debugText.add(DebugText("get__et_m      : ${ctx.client.get__et_m()}"))
        debugText.add(DebugText("get__fv_rn     : ${ctx.client.get__fv_rn()}"))
        debugText.add(DebugText("get__fw_rt     : ${ctx.client.get__fw_rt()}"))
        debugText.add(DebugText("get__gx_i      : ${ctx.client.get__gx_i()}"))
        debugText.add(DebugText("get__gx_o      : ${ctx.client.get__gx_o()}"))
        debugText.add(DebugText("get__gb_o      : ${ctx.client.get__gb_o()}"))
        debugText.add(DebugText("get__gg_sm     : ${ctx.client.get__gg_sm()}"))
        debugText.add(DebugText("get__gi_i      : ${ctx.client.get__gi_i()}"))
        debugText.add(DebugText("get__hp_o      : ${ctx.client.get__hp_o()}"))
        debugText.add(DebugText("get__ho_ns     : ${ctx.client.get__ho_ns()}"))
        debugText.add(DebugText("get__hz_w      : ${ctx.client.get__hz_w()}"))
        debugText.add(DebugText("get__ha_cq     : ${ctx.client.get__ha_cq()}"))
        debugText.add(DebugText("get__hu_rs     : ${ctx.client.get__hu_rs()}"))
        debugText.add(DebugText("get__hs_g      : ${ctx.client.get__hs_g()}"))
        debugText.add(DebugText("get__hw_mx     : ${ctx.client.get__hw_mx()}"))
        debugText.add(DebugText("get__ia_d      : ${ctx.client.get__ia_d()}"))
        debugText.add(DebugText("get__ic_y      : ${ctx.client.get__ic_y()}"))
        debugText.add(DebugText("get__ip_s      : ${ctx.client.get__ip_s()}"))
        debugText.add(DebugText("get__ii_f      : ${ctx.client.get__ii_f()}"))
        debugText.add(DebugText("get__ii_k      : ${ctx.client.get__ii_k()}"))
        debugText.add(DebugText("get__ik_k      : ${ctx.client.get__ik_k()}"))
        debugText.add(DebugText("get__ih_gs     : ${ctx.client.get__ih_gs()}"))
        debugText.add(DebugText("get__ie_s      : ${ctx.client.get__ie_s()}"))
        debugText.add(DebugText("get__js_rj     : ${ctx.client.get__js_rj()}"))
        debugText.add(DebugText("get__kz_v      : ${ctx.client.get__kz_v()}"))
        debugText.add(DebugText("get__lj_k      : ${ctx.client.get__lj_k()}"))
        debugText.add(DebugText("get__lj_s      : ${ctx.client.get__lj_s()}"))
        debugText.add(DebugText("get__lj_z      : ${ctx.client.get__lj_z()}"))
        debugText.add(DebugText("get__lh_r      : ${ctx.client.get__lh_r()}"))

//        debugText.add(DebugText("clientData.loginState :${ctx.client.get__a}"))


        val yBase = 550
        var x = 50
        var y = yBase
        var bannedY = 50
        var bannedX = 50
        val savedFont = g.font
        g.font = Font("Consolas", Font.PLAIN, 10)
        debugText.forEachIndexed { index, debugText ->
            g.color = Color.ORANGE

            g.drawString(debugText.text, x, y)
            //Loginto a banned account and we can get this result
            if(debugText.text.contains("12")){
                g.drawString(debugText.text, bannedX, bannedY)
                bannedY+= 15
            }
            y += 15
            if(index > 0 && index % 20 == 0){
                y = yBase
                x += 150
            }
        }
        g.font = savedFont
    }


}
