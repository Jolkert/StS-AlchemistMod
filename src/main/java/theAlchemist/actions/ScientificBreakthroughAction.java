package theAlchemist.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAlchemist.enums.AlchemistCardTags;

import java.util.Iterator;

public class ScientificBreakthroughAction extends AbstractGameAction
{// This code is literally just copy-pasted from ScrapeFollowUpAction with the if statement changed, so I hope it doesn't completely break everything -Jolkert 2020-02-09
	public ScientificBreakthroughAction()
	{
		this.duration = 0.001F;
	}
	
	public void update()
	{
		AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
		this.tickDuration();
		if (this.isDone)
		{
			
			for(AbstractCard c : DrawCardAction.drawnCards)
			{
				if(!c.hasTag(AlchemistCardTags.ELEMENT))
				{
					AbstractDungeon.player.hand.moveToDiscardPile(c);
					c.triggerOnManualDiscard();
					GameActionManager.incrementDiscard(false);
				}
				else
					c.upgrade();
			}
		}
		
	}
}
