package theAlchemist.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import theAlchemist.cards.AbstractElement;

public class AerAction extends AbstractGameAction
{// Basically of of this code is a copy-paste of FollowUp's code, so some of it might have to change later idk -Jolkert 2020-02-06
	public AerAction() {}
	
	public void update()
	{
		if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2
				&& (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)) instanceof AbstractElement)
		{
			this.addToTop(new GainEnergyAction(1));
			
			if (Settings.FAST_MODE)
				this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.0F));
			else
				this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.3F));
		}
		
		this.isDone = true;
	}
}
